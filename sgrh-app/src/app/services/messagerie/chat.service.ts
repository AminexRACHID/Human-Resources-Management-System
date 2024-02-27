import { Injectable } from '@angular/core';
import * as Stomp from 'stompjs';
import SockJS from 'sockjs-client';
import {interval, Observable, Subject} from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})

export class ChatService {

  private stompClient: any;
  private baseUrl = 'http://localhost:8088/ws'; // Update with your WebSocket endpoint
  private usersUrl = 'http://localhost:8088/users';
  private messageSubject = new Subject<any>();

  // Subject for handling connected users
  private connectedUsersSubject = new Subject<any[]>();
  public connectedUsers: any[] = [];

  constructor(private httpClient: HttpClient) {

  }

  private initializeWebSocketConnection() {
    const socket = new SockJS(this.baseUrl);
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({}, (frame: any) => {
      console.log('Connected to server', frame);
    }, (error: any) => {
      console.error('Error connecting to server', error);
    });
  }

  // sendMessage(destination: string, message: any) {
  //   if (this.stompClient && this.stompClient.connected) {
  //     this.stompClient.send(destination, {}, JSON.stringify(message));
  //   }
  // }
  sendMessage(message: any) {
    if (this.stompClient && this.stompClient.connected) {
      this.stompClient.send("/app/chat", {}, JSON.stringify(message));
    }
  }

  subscribeToMessages(destination: string) {
    this.stompClient.subscribe(destination, (message: any) => {
      this.messageSubject.next(JSON.parse(message.body));
    });
  }

  getMessageSubject() {
    return this.messageSubject.asObservable();
  }

  // New method to fetch connected users using HTTP
  fetchConnectedUsers() {
    return this.httpClient.get<any[]>(this.usersUrl);
  }
  getConnectedUsersSubject() {
    return this.connectedUsersSubject.asObservable();
  }
  connect(nickname: string, fullname: string) {
    // Perform any necessary actions on connecting
    // For example, sending user information to the server
    this.initializeWebSocketConnection();



    setTimeout(() => {
      this.stompClient.send("/app/user.addUser",
        {},
        JSON.stringify({ nickName: nickname, fullName: fullname, status: 'ONLINE' })
      );
    },1000);

  }

  downloadAttachment(id: string): void {
    this.httpClient.get('http://localhost:8088/attachement' + id, {
      responseType: 'blob', // Ensure responseType is set to 'blob'
      observe: 'response'   // Add observe: 'response' to get full HTTP response
    }).subscribe(response => {
      // Accessing Content-Disposition header
      const contentDispositionHeader: string | null = response.headers.get('Content-Disposition');
      console.log('Content-Disposition header:', contentDispositionHeader);

      // You can handle the file content here, for example, saving it or displaying it
      // @ts-ignore
      const file = new Blob([response.body], { type: 'application/octet-stream' });
      // Example of how to create a URL for the blob object and trigger a download
      const fileURL = URL.createObjectURL(file);
      window.open(fileURL);
    });
  }



  addAttachment(file: any): Observable<string> {
    // const formData: FormData = new FormData();
    // formData.append('attach', file, file.name);
    return this.httpClient.post<string>('http://localhost:8088/attachement', file);
  }

  // downloadAttachment(id: string): Observable<Blob> {
  //   return this.httpClient.get(`http://localhost:8088/attachement/${id}`, { responseType: 'blob' });
  // }

  deleteAttachment(id: string): Observable<any> {
    return this.httpClient.delete<any>('http://localhost:8088/attachement' + `/${id}`);
  }

  // //
  // sendMessageToAll(nickname: string, messageContent: string, fileInputToAll: File) {
  //   // Send a message to all users
  //
  //   const chatMessage = {
  //     senderId: nickname,
  //     content: messageContent,
  //     timestamp: new Date()
  //   };
  //
  //   if (fileInputToAll) {
  //     // Handle file upload if needed
  //   }
  //
  //   this.stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));
  // }


  fetchAndDisplayUserChat(nickname: string, selectedUserId: string) {
    return this.httpClient.get<any>('http://localhost:8088' + `/messages/${nickname}/${selectedUserId}`);
  }

  disconnect(nickname: string, fullname: string) {
    this.stompClient.send("/app/user.disconnectUser",
      {},
      JSON.stringify({ nickName: nickname, fullName: fullname, status: 'OFFLINE' })
    );
    this.stompClient.disconnect(() => {
    });
    setTimeout(() => {

      this.stompClient = null;
    },1000);
  }



  getConnectedUsers(): Observable<any[]> {
    return this.httpClient.get<any[]>(this.usersUrl)
  }

  getAllUsers(): Observable<any[]> {
    return this.httpClient.get<any[]>('http://localhost:8088/allUsers');
  }

  getAttachementName(id : string): Observable<string> {
    return this.httpClient.get<string>('http://localhost:8088/attachement/name/'+`${id}`);
  }

  deleteMessage(idMessage: string){
    return this.httpClient.delete<any>('http://localhost:8088/deleteMessage' + `/${idMessage}`);
  }


}
