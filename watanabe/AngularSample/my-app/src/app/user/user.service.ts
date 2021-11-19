import { Injectable } from '@angular/core';
import { User } from './user';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  users: User[];
  constructor() {
    this.users = [
      { id: 1, name: 'Yamada', email: 'yamada@example.com' },
      { id: 2, name: 'Suzuki', email: 'suzuki@example.com' },
      { id: 3, name: 'Tanaka', email: 'tanaka@example.com' },
    ];
  }
  getUsers(): User[] {
    return this.users;
  }
  getUser(id: number): User {
    let user = this.users.find((user) => user.id === id);
    if (user) {
      return user;
    } else {
      return new User;
    }
  }
  setUser(user: User): void {
    for (let i = 0; i < this.users.length; i++) {
      if (this.users[i].id === user.id) {
        this.users[i] = user;
      }
    }
  }
}
