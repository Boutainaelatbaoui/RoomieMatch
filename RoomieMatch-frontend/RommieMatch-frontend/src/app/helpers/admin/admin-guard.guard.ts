import { CanActivateFn, Router } from '@angular/router';
import { StorageService } from 'src/app/services/storage/storage.service';

export const adminGuardGuard: CanActivateFn = (route, state) => {
  const storageService = new StorageService(); 
  const router = new Router();

  const user =storageService.getSavedUser();
  const permissions = storageService.getPermissions();
  console.log(permissions);
  
  if (user && permissions && permissions.includes('CAN_UPDATE') ) {
    return true;
  } else {
    router.navigate(['/forbidden']);
    return false;
  }
};
