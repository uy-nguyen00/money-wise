import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";

@Component({
  selector: 'app-activate-account',
  templateUrl: './activate-account.component.html',
  styleUrl: './activate-account.component.scss'
})
export class ActivateAccountComponent {

  message: string = '';
  isOkay: boolean = true;
  submitted: boolean = false;

  constructor(
    private router: Router,
    private authService: AuthenticationService
  ) {
  }

  onCodeCompleted(code: string) {
    this.confirmAccount(code);
  }

  redirectToLogin() {
    this.router.navigate(['/login']);
  }

  private confirmAccount(code: string) {
    this.authService.confirm({
      code
    }).subscribe({
      next: () => {
        this.message = 'Your account has been activated.\nNow you can login.';
        this.submitted = true;
        this.isOkay = true;
      },
      error: () => {
        this.message = 'Activation code has been expired or invalid.\nPlease try again.';
        this.submitted = true;
        this.isOkay = false;
      }
    })
  }
}
