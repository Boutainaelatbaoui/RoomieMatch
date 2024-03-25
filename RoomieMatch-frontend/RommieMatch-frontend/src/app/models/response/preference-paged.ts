import { PreferenceResponse } from "./preference-response";

export interface PreferencePaged {
    content: PreferenceResponse[];
    pageable: any;
    last: boolean;
    totalElements: number;
    totalPages: number;
    size: number;
    number: number;
    sort: any;
    first: boolean;
    numberOfElements: number;
    empty: boolean;
}
