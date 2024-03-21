import { RequestStatus } from "src/app/enums/RequestStatus";

export interface RequestRequest {
    senderEmail: string;
    recipientEmail: string;
    status: RequestStatus;
    createdAt: Date;
    message: string;
}