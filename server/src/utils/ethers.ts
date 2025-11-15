import { ethers } from "ethers";
import { Abi__factory } from "@/lib/types/contract";
import {
  ALCHEMY_API_URL,
  CONTRACT_ADDRESS,
  PRIVATE_KEY,
} from "@/utils/constants";

const provider = new ethers.JsonRpcProvider(ALCHEMY_API_URL);
const signer = new ethers.Wallet(PRIVATE_KEY, provider);
export const contract = Abi__factory.connect(CONTRACT_ADDRESS, signer);

export const createHash = (input: string) => {
  return ethers.keccak256(ethers.toUtf8Bytes(input));
};
