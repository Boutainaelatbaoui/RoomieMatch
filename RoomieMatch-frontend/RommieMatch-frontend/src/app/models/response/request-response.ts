import { RequestStatus } from "src/app/enums/RequestStatus";
import { UserResponse } from "./user-response";

export interface RequestResponse {
    id: number;
    sender: UserResponse;
    recipient: UserResponse;
    status: RequestStatus;
    createdAt: Date;
    message: string;
}
