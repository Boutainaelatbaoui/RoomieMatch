import { QuestionRequest } from "../request/question-request";

export interface ChoiceResponse {
    id: number;
    choiceText: string;
    question: QuestionRequest;
}
