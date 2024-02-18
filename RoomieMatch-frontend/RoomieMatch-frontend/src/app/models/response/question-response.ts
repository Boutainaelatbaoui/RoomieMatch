import { ChoiceResponse } from "./choice-response";

export interface QuestionResponse {
    id: number;
    questionText: string;
    choices: ChoiceResponse[];
}
