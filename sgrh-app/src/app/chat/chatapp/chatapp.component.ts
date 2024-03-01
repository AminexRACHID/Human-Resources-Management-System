import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";
import {interval, Observable} from "rxjs";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {ChatService} from "../../services/messagerie/chat.service";
import {EmployeeService} from "../../services/employees/employee.service";
import {AuthService} from "../../services/authentification/auth.service";
import {StagiaireService} from "../../services/stagiaires/stagiaire.service";


@Component({
  selector: 'app-chatapp',
  templateUrl: './chatapp.component.html',
  styleUrl: './chatapp.component.css'
})
export class ChatappComponent implements OnInit,AfterViewInit {
  nickname: string = '';
  fullname: string = '';
  selectedUserId: string = '';
  messageContent: string = '';
  messageContentOne: string = '';
  // @ts-ignore
  fileInputToAll: any;
  // @ts-ignore
  fileInput: any;
  // @ts-ignore
  connectedUsers: any[] = [];
  messages: any[] = [];
  fileId: any;
  private payload: any;
  private status: any;
  selectedFullName: string = '';
  allUsers: any[] = [];
  selectedStatus: string = '';




  constructor(private websocketService: ChatService,private http: HttpClient, private employeeService:EmployeeService, public authService:AuthService, private stagiaireService:StagiaireService) {}

  ngAfterViewInit(): void {
    // console.log('ssssssssssssssssssssssssssssssss')
    setTimeout(() => {
      interval(3000).subscribe(() => {
        this.renderusersandmessages();
      });
    },1000);

  }

  ngOnInit() {

    // console.log("NgGGgGgGGGGGGGGGGGGGGGGGGGGGG")

    this.websocketService.getMessageSubject().subscribe((message) => {
      // console.log("NgGGgGgGGGGGGGGGGGGGGGGGGGGGG")

      if (message && message.status === 'ONLINE') {
        this.addConnectedUser(message);
        this.getUsersConnected();
        this.getAllUsers();
      } else if (message && message.status === 'OFFLINE') {
        this.getUsersConnected();
        this.removeConnectedUser(message.nickName);
        this.getAllUsers();
      }else{
        this.getUsersConnected();
        this.getAllUsers();
      }

    });

    this.getName();
    setTimeout(() => {
      this.connect();
    },700);

  }

  renderusersandmessages() {
    if(this.selectedUserId){
      this.fetchAndDisplayUserChat();
      this.onMessageReceived(this.payload).then(r => {});
    }
    else {
      this.getUsersConnected();
      this.getAllUsers();
      //this.onMessageReceived(this.payload).then(r => {});
    }
    //this.fetchAndDisplayUserChat();
    this.getUsersConnected();
    this.getAllUsers();


  }

  getUsersConnected(){
    this.websocketService.getConnectedUsers().subscribe((users) => {
      const existingUserStatusMap = new Map<string, boolean>();
      this.connectedUsers.forEach(user => {
        existingUserStatusMap.set(user.nickName, user.isSelected);
      });

      // Update the connectedUsers array
      this.connectedUsers = users.map(user => ({
        ...user,
        isSelected: existingUserStatusMap.has(user.nickName) ? existingUserStatusMap.get(user.nickName) : false
      }));
      this.connectedUsers = this.connectedUsers.filter((user) => user.nickName !== this.nickname);
    });
  }



  getAllUsers(){
    this.websocketService.getAllUsers().subscribe((users) => {
      const existingUserStatusMap = new Map<string, boolean>();
      this.allUsers.forEach(user => {
        existingUserStatusMap.set(user.nickName, user.isSelected);
      });

      // Update the connectedUsers array
      this.allUsers = users.map(user => ({
        ...user,
        isSelected: existingUserStatusMap.has(user.nickName) ? existingUserStatusMap.get(user.nickName) : false
      }));
      this.allUsers = this.allUsers.filter((user) => user.nickName !== this.nickname && user.status !== 'ONLINE');
    });
  }



  private addConnectedUser(user: any): void {
    const existingUser = this.connectedUsers.find((u) => u.nickName === user.nickName);
    if (!existingUser) {
      this.connectedUsers.push(user);
      this.getUsersConnected();
      this.getAllUsers();
    }
  }

  private removeConnectedUser(nickName: string): void {
    this.connectedUsers = this.connectedUsers.filter((user) => user.nickName !== nickName);
  }


  connect() {
    if (this.nickname || this.fullname) {
      this.getUsersConnected();
      this.getAllUsers();
      this.websocketService.connect(this.nickname, this.fullname);
      // const user = this.connectedUsers.filter((user) => user.nickName === this.nickname);
      // user[0].status = 'ONLINE'

      const usernamePage = document.querySelector('#username-page');
      const chatPage = document.querySelector('#chat-page');
      if (usernamePage && chatPage) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');}
      // Fetch and display the list of connected users

      // Fetch connected users initially
      // this.websocketService.getConnectedUsers().subscribe((users) => {
      //   this.connectedUsers = users;
      //   this.connectedUsers = this.connectedUsers.filter((user) => user.nickName !== this.nickname);
      // });

      // this.websocketService.getConnectedUsers().subscribe((users) => {
      //     this.connectedUsers = users.map(user => ({ ...user, isSelected: false }));
      //     this.connectedUsers = this.connectedUsers.filter((user) => user.nickName !== this.nickname);
      // });


    }
    // this.renderusersandmessages();
    // this.fetchAndDisplayUserChat();
    // this.onMessageReceived(this.payload).then(r => {});
    // this.getUsersConnected();
  }

  getSelectedUserNicknames(): string[] {
    return this.connectedUsers
      .filter(user => user.isSelected) // Filtrer les utilisateurs cochés
      .map(user => user.nickName); // Récupérer les noms des utilisateurs cochés
  }
  getSelectedAllUserNicknames(): string[] {
    return this.allUsers
      .filter(user => user.isSelected) // Filtrer les utilisateurs cochés
      .map(user => user.nickName); // Récupérer les noms des utilisateurs cochés
  }
  sendMessageToAll() {
    if (this.messageContent || this.fileInputToAll) {
      const selectedUserNicknames = this.getSelectedUserNicknames();
      const selectedOffLineUsers = this.getSelectedAllUserNicknames();

      setTimeout(() => {
        this.saveAttachment();
        setTimeout(() => {
          selectedUserNicknames.forEach((user:any) =>{
            console.log("to online");
            this.sendMessageToMultipel(user);
          });
        },300);

      },500);
      setTimeout(() => {
        this.saveAttachment();
        selectedOffLineUsers.forEach((user:any) =>{
          console.log("send Message to offline")
          this.sendMessageToMultipel(user);
        });
      },500);

      setTimeout(() => {
        this.messageContent = '';
        this.fileId ='';
      },1000);

    }


    setTimeout(()=>{
      const fileInput = document.getElementById('fileInputToAll') as HTMLInputElement;

      // Reset the file input element
      if (fileInput) {
        fileInput.value = '';
        this.fileInputToAll='';
      }
    },500)
  }


  userItemClick(userId: string, userName: string, status: string) {
    this.selectedFullName = userName;
    this.selectedUserId = userId;
    this.selectedStatus = status;
    this.fetchAndDisplayUserChat();
    setTimeout(() =>{
      this.scrollToBottom();
    }, 300);
  }
  scrollToBottom() {
    const chatMessages = document.getElementById('chat-messages');

    if (chatMessages) {
      chatMessages.scrollTop = chatMessages.scrollHeight;
    }
  }
  // Inside your component class
  isImageAttachment(attachmentId: string): boolean {
    // Check if the attachment has an image extension (e.g., png, jpg, jpeg, gif, etc.)
    const imageExtensions = ['png', 'jpg', 'jpeg', 'gif'];
    const extension = attachmentId.split('.').pop()?.toLowerCase();
    return extension ? imageExtensions.includes(extension) : false;
  }

  isVideoAttachment(attachmentId: string): boolean {
    // Check if the attachment has a video extension (e.g., mp4, webm, etc.)
    const videoExtensions = ['mp4', 'webm'];
    const extension = attachmentId.split('.').pop()?.toLowerCase();
    return extension ? videoExtensions.includes(extension) : false;
  }
  fetchAndDisplayUserChat() {

    const chatArea = document.querySelector('.chat-area') as HTMLInputElement;
    const chaaaaat = document.querySelector('#message-input-one');
    const username = document.querySelector('.selected-user-bar') as HTMLInputElement;
    username.classList.remove("hidden");
    // @ts-ignore
    chaaaaat.classList.remove("hidden");
    // @ts-ignore
    chatArea.classList.remove("hidden");

    this.websocketService.fetchAndDisplayUserChat(this.nickname, this.selectedUserId).subscribe(
      (messages: any[]) => {
        this.messages = messages;

      });
  }

  private handleErrorFetchingConnectedUsers(response?: any) {
    // Add your logic to handle non-JSON responses (possibly HTML)
    console.error('Error fetching connected users. Response:', response);
    // You can display an error message to the user or take appropriate action
  }

  onLogout() {
    this.websocketService.disconnect(this.nickname, this.fullname);
    // this.getUsersConnected();
    // this.getAllUsers();
    // const usernamePage = document.querySelector('#username-page');
    // const chatPage = document.querySelector('#chat-page');
    // if (usernamePage && chatPage) {
    //   usernamePage.classList.remove('hidden');
    //   chatPage.classList.add('hidden');
    // }

  }

  // displayMessage(senderId: string, content: string, attachmentId: string) {
  //   // Implement how you want to display messages in the chat area
  //   const messageContainer = document.createElement('div');
  //   messageContainer.classList.add('message');
  //   if (senderId === this.nickname) {
  //     messageContainer.classList.add('sender');
  //   } else {
  //     messageContainer.classList.add('receiver');
  //   }
  //   const message = document.createElement('p');
  //   message.textContent = content;
  //   messageContainer.appendChild(message);
  //
  //   if (attachmentId) {
  //     const downloadImage = document.createElement('img');
  //     downloadImage.src = '../img/download.png';
  //     downloadImage.alt = 'Download';
  //     downloadImage.style.marginLeft = '10px';
  //     downloadImage.style.marginRight = '10px';
  //     downloadImage.style.width = '15px';
  //     downloadImage.style.height = '15px';
  //     downloadImage.addEventListener('click', () => this.downloadAttachment(attachmentId));
  //     messageContainer.appendChild(downloadImage);
  //   }
  //
  //   // @ts-ignore
  //   document.querySelector('#chat-messages').appendChild(messageContainer);
  //
  // }

  saveAttachment() {
    if (this.fileInputToAll) {
      const formData = new FormData();
      formData.append('attach', this.fileInputToAll);
      this.websocketService.addAttachment(formData).subscribe(
        (response:any) => {
          console.log(response.id)
          this.fileId = response.id;
        },
        (error) => {
          console.error(error);
        }
      );
    }
  }

  saveAttachmentOne() {
    console.log("avant Condition on save attachement")
    if (this.fileInput) {
      console.log("Aprés condition")
      const formData = new FormData();
      formData.append('attach', this.fileInput);
      this.websocketService.addAttachment(formData).subscribe(
        (response:any) => {
          console.log(response.id)
          this.fileId = response.id;

        },
        (error) => {
          console.error(error);
        }
      );
    }
  }

  // async downloadAttachment(attachmentId: string) {
  //   // console.log(attachmentId);
  //   const response = await fetch(`http://localhost:8088/attachement/${attachmentId}`);
  //
  //   if (response.ok) {
  //     // const contentDisposition = response.headers.get('content-disposition');
  //     // console.log(contentDisposition);
  //     // const filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
  //     // // @ts-ignore
  //     // const matches = filenameRegex.exec(contentDisposition);
  //     // const filename = matches && matches[1] ? matches[1].replace(/['"]/g, '') : 'attachment';
  //     let filename = '';
  //
  //     this.websocketService.getAttachementName(attachmentId).subscribe(
  //       (response1:any) => {
  //         console.log(response1.fileName)
  //         filename = response1.fileName;
  //
  //       },
  //       (error) => {
  //         console.error(error);
  //       }
  //     );
  //     const blob: any = response.blob();
  //     const url = window.URL.createObjectURL(blob);
  //     const a = document.createElement('a');
  //     a.href = url;
  //     // @ts-ignore
  //     a.download = filename;
  //     a.classList.add('download-button');
  //     document.body.appendChild(a);
  //     a.click();
  //     window.URL.revokeObjectURL(url);
  //   } else {
  //     console.error('Error downloading attachment');
  //   }
  // }

  async downloadAttachment(attachmentId: string) {
    const response = await fetch(`http://localhost:8088/attachement/${attachmentId}`);
    if (response.ok) {


      let filename = '';

      this.websocketService.getAttachementName(attachmentId).subscribe(
        (response1:any) => {
          filename = response1.fileName;
          console.log("web "+filename);
        },
        (error) => {
          console.error(error);
        }
      );
      const blob = await response.blob();

      setTimeout(()=>{
        console.log(filename)
        const url = window.URL.createObjectURL(blob);

        // Create a link element
        const a = document.createElement('a');
        a.href = url;
        // @ts-ignore
        a.download = filename; // Set the filename
        a.classList.add('download-button'); // Add CSS class
        document.body.appendChild(a);
        a.click(); // Simulate click to trigger download
        window.URL.revokeObjectURL(url); // Release the object URL
      }, 300);
    } else {
      console.error('Error downloading attachment');
    }
  }

  // async downloadAttachment(attachmentId: string) {
  //   try {
  //     // Make the HTTP request
  //     const response = await this.http.get('http://localhost:8088/attachement/' + attachmentId, {
  //       responseType: 'blob', // Ensure responseType is set to 'blob'
  //       observe: 'response'   // Add observe: 'response' to get full HTTP response
  //     }).toPromise();
  //
  //     // Check if the request was successful (status code 200)
  //     // @ts-ignore
  //     if (response.status === 200) {
  //       // @ts-ignore
  //       const blob = await response.body;
  //       // @ts-ignore
  //       const filename = this.getFilenameFromContentDisposition(response.headers.get('content-Disposition'));
  //       console.log(filename)
  //       // Create a URL for the blob object
  //       // @ts-ignore
  //       const url = window.URL.createObjectURL(blob);
  //
  //       // Create a link element
  //       const a = document.createElement('a');
  //       a.href = url;
  //       a.download = filename; // Set the filename
  //       a.classList.add('download-button'); // Add CSS class
  //       document.body.appendChild(a);
  //       a.click(); // Simulate click to trigger download
  //       window.URL.revokeObjectURL(url); // Release the object URL
  //     } else {
  //       console.error('Error downloading attachment');
  //     }
  //   } catch (error) {
  //     console.error('Error downloading attachment:', error);
  //   }
  // }


  sendMessage() {
    this.saveAttachmentOne();

    setTimeout(() => {
      if (this.messageContentOne || this.fileInput) {
        const chatMessage = {
          senderId: this.nickname,
          recipientId: this.selectedUserId,
          content: this.messageContentOne,
          attachementId: this.fileId,
          // ? this.saveAttachment('All') : null,
          timestamp: new Date()
        };

        // @ts-ignore
        this.websocketService.sendMessage(chatMessage);
        // Clear message input after sending
        this.messageContentOne = '';
      }
      setTimeout(() => {
        this.fileId = '';
      },300);

    },500);
    setTimeout(()=>{
      const fileInput = document.getElementById('fileInput') as HTMLInputElement;

      // Reset the file input element
      if (fileInput) {
        fileInput.value = '';
        this.fileInput='';
      }
    },500)
  }

  sendMessageToMultipel(recepient:any) {
    if (this.messageContent || this.fileInput) {
      const chatMessage = {
        senderId: this.nickname,
        recipientId: recepient,
        content: this.messageContent,
        attachementId: this.fileId,
        timestamp: new Date()
      };
      console.log("hana dkhelt")
      // @ts-ignore
      this.websocketService.sendMessage(chatMessage);
    }
  }

  async onMessageReceived(payload: any): Promise<void> {



    const message: { senderId: string; content: string } = JSON.parse(payload.body);
    if (this.selectedUserId && this.selectedUserId === message.senderId) {
      this.fetchAndDisplayUserChat();

    }

    if (this.selectedUserId) {
      const selectedUserElement = document.querySelector(`#${this.selectedUserId}`);


      if (selectedUserElement) {
        selectedUserElement.classList.add('active');

      }
    } else {
      const messageForm = document.querySelector(`#messageForm`);
      // @ts-ignore
      messageForm.classList.add('hidden');
    }

    const notifiedUser = document.querySelector(`#${message.senderId}`);
    if (notifiedUser && !notifiedUser.classList.contains('active')) {
      const nbrMsg = notifiedUser.querySelector('.nbr-msg');
      if (nbrMsg) {
        nbrMsg.classList.remove('hidden');
        nbrMsg.textContent = '';
      }
    }
  }

  onFileChange(event: any) {
    this.fileInputToAll = event.target.files[0];
    const input = event.target;
    const fileName = input.files[0]?.name;



    if (fileName) {
      const label = input.nextElementSibling.querySelector('.form-file-text');
      label.textContent = fileName;
    }
  }

  onFileChangeOne(event: any) {
    this.fileInput = event.target.files[0];
    const input = event.target;
    const fileName = input.files[0]?.name;



    if (fileName) {
      const label = input.nextElementSibling.querySelector('.form-file-text');
      label.textContent = fileName;
    }
  }

  onDeleteMessage(id: string): void {
    this.websocketService.deleteMessage(id).subscribe(
      () => {
        console.log('Message deleted successfully.');
        // Mettez ici tout code à exécuter après la suppression du message.
      },
      error => {
        console.error('An error occurred:', error);
        // Gérer l'erreur ici.
      }
    );
  }

  // downloadAttachmentFunc(attachementId: any) {
  //   this.downloadAttachment(attachementId);
  // }
  getAttachmentName(attachementId: any) {

    let filename = '';

    this.websocketService.getAttachementName(attachementId).subscribe(
      (response1:any) => {
        filename = response1.fileName;
        return filename;
      },
      (error) => {
        console.error(error);
      }
    );
  }

  // getAttachmentNamefile(attachmentId: any): Observable<string> {
  //   return new Observable((observer) => {
  //     this.websocketService.getAttachementName(attachmentId).subscribe(
  //       (response1: any) => {
  //         const filename = response1.fileName;
  //         console.log(filename)
  //         observer.next(filename);
  //         observer.complete();
  //       },
  //       (error) => {
  //         observer.error(error);
  //       }
  //     );
  //   });
  // }

  getName(){
    if (this.authService.roles == "Stagiaire"){
      this.stagiaireService.getCondidateByEmail(this.authService.username).subscribe({
        next: (data) => {
          this.fullname = data.firstName +' '+ data.lastName;
          this.nickname = data.firstName + data.lastName;
        },
        error: (err) => {
        }
      });
    } else {
      this.employeeService.getEmployeeByEmail(this.authService.username).subscribe({
        next: (data) => {
          this.fullname = data.firstName +' '+ data.lastName;
          this.nickname = data.firstName + data.lastName;
        },
        error: (err) => {
        }
      });
    }
  }
}
