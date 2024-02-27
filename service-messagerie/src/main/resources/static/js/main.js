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

    // Ajouter une checkbox avant la première entrée
    const selectAllBeforeContainer = document.createElement('div');
    selectAllBeforeContainer.classList.add('select-all-container');

    const selectAllBeforeText = document.createElement('span');
    selectAllBeforeText.textContent = 'Sélectionner tous';

    const selectAllBefore = document.createElement('input');
    selectAllBefore.type = 'checkbox';
    selectAllBefore.classList.add('select-all');
    selectAllBefore.addEventListener('change', selectAllUsers);

    const separator = document.createElement('li');
    separator.classList.add('separator');

    selectAllBeforeContainer.appendChild(selectAllBefore);
    selectAllBeforeContainer.appendChild(selectAllBeforeText);
    connectedUsersList.appendChild(selectAllBeforeContainer);
    connectedUsersList.appendChild(separator);


    connectedUsers.forEach(user => {
        appendUserElement(user, connectedUsersList);
        if (connectedUsers.indexOf(user) < connectedUsers.length - 1) {
            const separator = document.createElement('li');
            separator.classList.add('separator');
            connectedUsersList.appendChild(separator);
        }
    });
}

// Gestionnaire d'événements pour sélectionner tous les utilisateurs
function selectAllUsers(event) {
    const isChecked = event.target.checked;
    const checkboxes = document.querySelectorAll('.user-checkbox');
    checkboxes.forEach(checkbox => {
        checkbox.checked = isChecked;
    });
}

// Récupérez votre bouton "Envoyer"
const sendButton = document.getElementById('sendButton');

// Ajoutez un écouteur d'événement "click" au bouton
sendButton.addEventListener('click', sendMessageToSelectedUsers);



async function sendMessageToSelectedUsers(event) {
    event.preventDefault();

    let id = await saveAttachement('All');
    const messageContent = document.getElementById('messageContent').value.trim();
    const fileInputAll = document.getElementById('fileInputToAll');

    if ((messageContent || (fileInputAll.files.length > 0)) && stompClient) {
        const checkedUsers = document.querySelectorAll('.user-checkbox:checked');
        const chatMessage = {
            senderId: nickname,
            content: messageContent,
            attachementId: id,
            timestamp: new Date()
        };
        checkedUsers.forEach(user => {
            chatMessage.recipientId = user.getAttribute('data-user-id');
            stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));
        });
        document.getElementById('fileInputToAll').value = '';
        document.getElementById('messageContent').value = ''; // Efface le champ de texte après l'envoi du message
    }
}


function appendUserElement(user, connectedUsersList) {
    const listItem = document.createElement('li');
    listItem.classList.add('user-item');
    listItem.id = user.nickName;

    const checkbox = document.createElement('input');
    checkbox.type = 'checkbox';
    checkbox.classList.add('user-checkbox');
    checkbox.setAttribute('data-user-id', user.nickName);

    const userImage = document.createElement('img');
    userImage.src = '../img/user_icon.png';
    userImage.alt = user.fullName;

    const usernameSpan = document.createElement('span');
    usernameSpan.textContent = user.fullName;

    const receivedMsgs = document.createElement('span');
    receivedMsgs.textContent = '0';
    receivedMsgs.classList.add('nbr-msg', 'hidden');

    listItem.appendChild(checkbox);
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


// Fonction pour afficher un message avec un bouton de suppression
function displayMessage(senderId, content, messageId, attachmentId) {
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
    deleteImage.style.marginLeft = '10px';
    deleteImage.style.marginRight = '10px';
    deleteImage.style.width = '15px'; // Spécifiez la taille ici
    deleteImage.style.height = '15px'; // Spécifiez la taille ici
    deleteImage.addEventListener('click', () => deleteMessage(messageId)); // Appeler la fonction de suppression
    messageContainer.appendChild(deleteImage);

    if (attachmentId) {
        // Créer un bouton de téléchargement pour l'attachement
        const downloadImage = document.createElement('img');
        downloadImage.src = '../img/download.png';
        downloadImage.alt = 'Download'; // Texte alternatif pour l'accessibilité
        downloadImage.style.marginLeft = '10px';
        downloadImage.style.marginRight = '10px';
        downloadImage.style.width = '15px'; // Spécifiez la taille ici
        downloadImage.style.height = '15px'; // Spécifiez la taille ici
        downloadImage.addEventListener('click', () => downloadAttachment(attachmentId));
        messageContainer.appendChild(downloadImage);
    }

    chatArea.appendChild(messageContainer);
}


async function fetchAndDisplayUserChat() {
    const userChatResponse = await fetch(`/messages/${nickname}/${selectedUserId}`);
    const userChat = await userChatResponse.json();
    chatArea.innerHTML = '';
    userChat.forEach(chat => {
        displayMessage(chat.senderId, chat.content, chat.id, chat.attachementId);
    });
    chatArea.scrollTop = chatArea.scrollHeight;
}


function onError() {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

async function saveAttachement(mlk) {
    if (mlk === 'notAll') {
        const fileInput = document.getElementById('fileInput');
        if (fileInput.files.length > 0) {
            const formData = new FormData();
            formData.append('attach', fileInput.files[0]);
            // Envoyer l'attachement au serveur
            const attachementResponse = await fetch('/attachement', {
                method: 'POST',
                body: formData
            });
            const data = await attachementResponse.json(); // Récupérer l'objet JSON retourné par le serveur
            const iddd = data.id;
            return iddd; // Retourner l'ID de l'attachement
        }
        return null; // Retourner null si aucun fichier n'est sélectionné
    }
    if (mlk === 'All'){
        const fileInput = document.getElementById('fileInputToAll');
        if (fileInput.files.length > 0) {
            const formData = new FormData();
            formData.append('attach', fileInput.files[0]);
            // Envoyer l'attachement au serveur
            const attachementResponse = await fetch('/attachement', {
                method: 'POST',
                body: formData
            });
            const data = await attachementResponse.json(); // Récupérer l'objet JSON retourné par le serveur
            const iddd = data.id;
            return iddd; // Retourner l'ID de l'attachement
        }
        return null; // Retourner null si aucun fichier n'est sélectionné
    }

}

async function downloadAttachment(attachementId) {
    const response = await fetch(`/attachement/${attachementId}`);

    if (response.ok) {
        const contentDisposition = response.headers.get('content-disposition');
        const filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
        const matches = filenameRegex.exec(contentDisposition);
        const filename = matches && matches[1] ? matches[1].replace(/['"]/g, '') : 'attachement';

        const blob = await response.blob();
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = filename; // Utiliser le nom du fichier renvoyé par le serveur
        a.classList.add('download-button'); // Ajouter la classe CSS
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
    } else {
        console.error('Erreur lors du téléchargement de l\'attachement');
    }
}



async function sendMessage(event) {
    event.preventDefault();

    let id = await saveAttachement('notAll');
    const messageContent = messageInput.value.trim();
    const fileInput = document.getElementById('fileInput');
    if ((messageContent || fileInput.files.length > 0) && stompClient) {
        const chatMessage = {
            senderId: nickname,
            recipientId: selectedUserId,
            content: messageInput.value.trim(),
            attachementId: id,
            timestamp: new Date()
        };
        stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));
        displayMessage(nickname, messageInput.value.trim(), chatMessage.id, chatMessage.attachementId);
        messageInput.value = '';
    }

    chatArea.scrollTop = chatArea.scrollHeight;
    event.preventDefault();
}


async function onMessageReceived(payload) {
    await findAndDisplayConnectedUsers();

    const message = JSON.parse(payload.body);
    console.log('Message received', payload);
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
