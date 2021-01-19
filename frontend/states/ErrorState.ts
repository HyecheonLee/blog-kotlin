import { atom } from "recoil";

export type ErrorType = {
  timestamp: string;
  status: number | null;
  error: string | null;
  message: string | null,
  path: string | null,
  isError: boolean
}
export const errorAuth = {
  timestamp: "",
  status: -100,
  error: "token expired",
  message: "로그인을 다시 해주세요",
  path: "",
  isError: true
}
export const errorInit = {
  timestamp: "",
  status: -1,
  error: "",
  message: "",
  path: "",
  isError: false
};
const errorState = atom<ErrorType>({
  key: "errorState",
  default: errorInit
});
export { errorState }