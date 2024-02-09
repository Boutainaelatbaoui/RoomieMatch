import { Role } from "./role";

export interface User {
    id: number,
    email: string,
    token: string,
    refreshToken: string,
}
