import { ChoiceResponse } from "./choice-response";
import { QuestionResponse } from "./question-response";


export interface QuestionnaireResponse{
    id?: number;
    userId: number;
    question: QuestionResponse;
    choice: ChoiceResponse;
}
