import { CityResponse } from './city-response';
import { PreferenceResponse } from './preference-response';

export interface UserResponse {
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
}
