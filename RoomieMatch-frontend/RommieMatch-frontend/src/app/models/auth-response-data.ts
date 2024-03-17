import { CityResponse } from "./response/city-response";
import { PreferenceResponse } from "./response/preference-response";

export interface AuthResponseData {
    id: number;
    firstName: string;
    lastName: string;
    telephone: string;
    bio: string;
    email: string;
    budget: number;
    occupation: number;
    gender: number;
    birthdate: string;
    profilePicture: string;
    currentCity: CityResponse;
    desiredCity: CityResponse;
    preference: PreferenceResponse;
    token_type: string;
    access_token: string,
    refresh_token: string
}
