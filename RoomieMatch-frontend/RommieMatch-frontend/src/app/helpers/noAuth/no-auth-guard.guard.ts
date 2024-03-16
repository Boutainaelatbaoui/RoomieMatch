import { CanActivateFn, Router } from '@angular/router';

export const noAuthGuardGuard: CanActivateFn = (route, state) => {
  const router = new Router();
  const user = localStorage.getItem('authenticated-user')
  if(!user){
    return true;
  } else{
    router.navigate(['/']);
    return false;
  }
};
