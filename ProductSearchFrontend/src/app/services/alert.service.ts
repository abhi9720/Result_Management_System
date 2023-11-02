import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AlertService {

  showAlert: boolean = false;
  alertType: string = '';
  alertMessage: string = '';

  showSuccess(message: string) {
    this.showAlert = true;
    this.alertType = 'success';
    this.alertMessage = message;
    this.triggerAutoClose();
  }

  showError(message: string) {
    this.showAlert = true;
    this.alertType = 'danger';
    this.alertMessage = message;
    this.triggerAutoClose();
  }

  hideAlert() {
    this.showAlert = false;
    this.alertType = '';
    this.alertMessage = '';
  }

  private triggerAutoClose() {
    setTimeout(() => {
      this.hideAlert();
    }, 5000); // 5 seconds delay
  }
}
