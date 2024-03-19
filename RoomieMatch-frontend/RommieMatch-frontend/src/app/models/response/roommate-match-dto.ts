import { UserResponse } from "./user-response";

export interface RoommateMatchDTO {
    user: UserResponse;
    matchScore: number;
    percentageMatch: number;
}
