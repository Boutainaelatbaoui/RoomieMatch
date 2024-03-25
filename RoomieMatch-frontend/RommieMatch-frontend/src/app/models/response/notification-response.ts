import { UserResponse } from "./user-response";

export interface NotificationResponseDTO {
    id: number;
    sender: UserResponse;
    recipient: UserResponse;
    message: string;
    read: boolean;
}
