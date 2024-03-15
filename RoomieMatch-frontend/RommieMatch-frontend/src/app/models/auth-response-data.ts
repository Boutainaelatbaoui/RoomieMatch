import { CityResponse } from "./response/city-response";

export interface AuthResponseData {
    id: number;
    email: string;
    firstName: string;
    lastName: string;
    telephone: string;
    bio: string;
    budget: number;
    occupation: number;
    gender: number;
    birthdate: Date;
    currentCity: CityResponse;
    desiredCity: CityResponse;
    token_type: string;
    access_token: string,
    refresh_token: string
}
