'use strict';

const usernamePage = document.querySelector('#username-page');
const chatPage = document.querySelector('#chat-page');
const usernameForm = document.querySelector('#usernameForm');
const messageForm = document.querySelector('#messageForm');
const messageInput = document.querySelector('#message');
const connectingElement = document.querySelector('.connecting');
const chatArea = document.querySelector('#chat-messages');
const logout = document.querySelector('#logout');

let stompClient = null;
let nickname = null;
let fullname = null;
let selectedUserId = null;

function connect(event) {
    nickname = document.querySelector('#nickname').value.trim();
    fullname = document.querySelector('#fullname').value.trim();

    if (nickname && fullname) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        const socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}


function onConnected() {
    stompClient.subscribe(`/user/${nickname}/queue/messages`, onMessageReceived);
    stompClient.subscribe(`/app/public`, onMessageReceived);

    // register the connected user
    stompClient.send("/app/user.addUser",
        {},
        JSON.stringify({nickName: nickname, fullName: fullname, status: 'ONLINE'})
    );
    document.querySelector('#connected-user-fullname').textContent = fullname;
    findAndDisplayConnectedUsers().then();
}

async function findAndDisplayConnectedUsers() {
    const connectedUsersResponse = await fetch('/users');
    let connectedUsers = await connectedUsersResponse.json();
    connectedUsers = connectedUsers.filter(user => user.nickName !== nickname);
    const connectedUsersList = document.getElementById('connectedUsers');
    connectedUsersList.innerHTML = '';

    connectedUsers.forEach(user => {
        appendUserElement(user, connectedUsersList);
        if (connectedUsers.indexOf(user) < connectedUsers.length - 1) {
            const separator = document.createElement('li');
            separator.classList.add('separator');
            connectedUsersList.appendChild(separator);
        }
    });
}

function appendUserElement(user, connectedUsersList) {
    const listItem = document.createElement('li');
    listItem.classList.add('user-item');
    listItem.id = user.nickName;

    const userImage = document.createElement('img');
    userImage.src = '../img/user_icon.png';
    userImage.alt = user.fullName;

    const usernameSpan = document.createElement('span');
    usernameSpan.textContent = user.fullName;

    const receivedMsgs = document.createElement('span');
    receivedMsgs.textContent = '0';
    receivedMsgs.classList.add('nbr-msg', 'hidden');

    listItem.appendChild(userImage);
    listItem.appendChild(usernameSpan);
    listItem.appendChild(receivedMsgs);

    listItem.addEventListener('click', userItemClick);

    connectedUsersList.appendChild(listItem);
}

function userItemClick(event) {
    document.querySelectorAll('.user-item').forEach(item => {
        item.classList.remove('active');
    });
    messageForm.classList.remove('hidden');

    const clickedUser = event.currentTarget;
    clickedUser.classList.add('active');

    selectedUserId = clickedUser.getAttribute('id');
    fetchAndDisplayUserChat().then();

    const nbrMsg = clickedUser.querySelector('.nbr-msg');
    nbrMsg.classList.add('hidden');
    nbrMsg.textContent = '0';

}

// Fonction pour supprimer un message
function deleteMessage(messageId) {
    // Envoyer une requête de suppression au serveur avec l'ID du message à supprimer
    fetch(`/deleteMessage/${messageId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (response.ok) {
                // Supprimer le message côté client si la suppression est réussie
                document.getElementById(messageId).remove();
            } else {
                // Gérer les erreurs de suppression
                console.error('Erreur lors de la suppression du message');
            }
        })
        .catch(error => {
            console.error('Erreur réseau :', error);
        });
}

// JavaScript

const toAllInput = document.getElementById('toAll');
const sendButton = document.getElementById('sendButton');

sendButton.addEventListener('click', sendMessageToAllUsers);

function sendMessageToAllUsers() {
    const messageContent = toAllInput.value.trim();
    if (messageContent && stompClient) {
        const chatMessage = {
            senderId: nickname,
            content: messageContent,
            timestamp: new Date()
        };

        // Récupérer la liste des utilisateurs connectés
        const connectedUsers = document.querySelectorAll('.user-item');

        // Envoyer le message à chaque utilisateur
        connectedUsers.forEach(user => {
            const userId = user.getAttribute('id');
            chatMessage.recipientId = userId;
            stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));
        });

        toAllInput.value = '';
    }
}



// Fonction pour afficher un message avec un bouton de suppression
function displayMessage(senderId, content, messageId) {
    const messageContainer = document.createElement('div');
    messageContainer.classList.add('message');
    if (senderId === nickname) {
        messageContainer.classList.add('sender');
    } else {
        messageContainer.classList.add('receiver');
    }
    const message = document.createElement('p');
    message.textContent = content;
    messageContainer.appendChild(message);

    // Créer l'image de suppression
    const deleteImage = document.createElement('img');
    deleteImage.src = '../img/delete.png'; // Remplacez par le chemin de votre image de suppression
    deleteImage.alt = 'Supprimer'; // Texte alternatif pour l'accessibilité
    deleteImage.style.marginLeft = '10px'
    deleteImage.style.marginRight = '10px'
    deleteImage.style.width = '15px'; // Définir la largeur de l'image
    deleteImage.style.height = '15px'; // Définir la hauteur de l'image
    deleteImage.addEventListener('click', () => deleteMessage(messageId)); // Appeler la fonction de suppression
    messageContainer.appendChild(deleteImage);

    chatArea.appendChild(messageContainer);
}
async function fetchAndDisplayUserChat() {
    const userChatResponse = await fetch(`/messages/${nickname}/${selectedUserId}`);
    const userChat = await userChatResponse.json();
    chatArea.innerHTML = '';
    userChat.forEach(chat => {
        displayMessage(chat.senderId, chat.content, chat.id);
    });
    chatArea.scrollTop = chatArea.scrollHeight;
}


function onError() {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

function saveAttachment() {
    const fileInput = document.getElementById('fileInput');
    if (fileInput.files.length > 0) {
        const formData = new FormData();
        formData.append('attachment', fileInput.files[0]);

        // Envoi de la requête AJAX pour enregistrer l'attachement
        return fetch('/attachment', {
            method: 'POST',
            body: formData
        })
            .then(response => {
                if (response.ok) {
                    return response.json(); // Retourne la réponse JSON contenant l'ID de l'attachement
                }
                throw new Error('Failed to save attachment');
            })
            .then(data => {
                return data.id; // Retourne l'ID de l'attachement depuis la réponse JSON
            })
            .catch(error => {
                console.error('Error saving attachment:', error);
                return null;
            });
    }
    return null;
}

async function sendMessage(event) {
    try {
        const id = await saveAttachment(); // Attendre que saveAttachment() renvoie une valeur
        const messageContent = messageInput.value.trim();
        if (messageContent && stompClient) {
            const chatMessage = {
                senderId: nickname,
                recipientId: selectedUserId,
                content: messageContent,
                attachmentId: id, // Utiliser le nom correct de l'attribut: attachmentId
                timestamp: new Date()
            };
            stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));
            displayMessage(nickname, messageInput.value.trim(), chatMessage.id);
            messageInput.value = '';
        }
        chatArea.scrollTop = chatArea.scrollHeight;
    } catch (error) {
        console.error('Error sending message:', error);
    }
    event.preventDefault();
}


async function onMessageReceived(payload) {
    await findAndDisplayConnectedUsers();
    console.log('Message received', payload);
    const message = JSON.parse(payload.body);
    if (selectedUserId && selectedUserId === message.senderId) {
        displayMessage(message.senderId, message.content);
        chatArea.scrollTop = chatArea.scrollHeight;
    }

    if (selectedUserId) {
        document.querySelector(`#${selectedUserId}`).classList.add('active');
    } else {
        messageForm.classList.add('hidden');
    }

    const notifiedUser = document.querySelector(`#${message.senderId}`);
    if (notifiedUser && !notifiedUser.classList.contains('active')) {
        const nbrMsg = notifiedUser.querySelector('.nbr-msg');
        nbrMsg.classList.remove('hidden');
        nbrMsg.textContent = '';
    }
}

function onLogout() {
    stompClient.send("/app/user.disconnectUser",
        {},
        JSON.stringify({nickName: nickname, fullName: fullname, status: 'OFFLINE'})
    );
    window.location.reload();
}

usernameForm.addEventListener('submit', connect, true); // step 1
messageForm.addEventListener('submit', sendMessage, true);
logout.addEventListener('click', onLogout, true);
window.onbeforeunload = () => onLogout();
