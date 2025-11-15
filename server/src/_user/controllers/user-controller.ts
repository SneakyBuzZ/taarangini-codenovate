import { DataResponse } from "@/utils/response";
import { Request, Response } from "express";
import { UserService } from "@/_user/services/user-service";

export class UserController {
  private userService: UserService;

  constructor() {
    this.userService = new UserService();
  }

  register = async (req: Request, res: Response) => {
    const token = await this.userService.register(req.body);
    res
      .status(201)
      .json(new DataResponse(201, token, "User created successfully"));
  };

  getAuthStatus = async (req: Request, res: Response) => {
    res.status(200).json(new DataResponse(200, {}, "Authorized"));
  };

  getAllUsers = async (req: Request, res: Response) => {
    const users = await this.userService.getAllUsers();
    res
      .status(200)
      .json(new DataResponse(200, users, "Users retrieved successfully"));
  };

  getUser = async (req: Request, res: Response) => {
    const userId = req.user?.id;
    if (!userId) {
      return res.status(401).json(new DataResponse(401, {}, "Unauthorized"));
    }
    const user = await this.userService.getUserById(userId);
    res
      .status(200)
      .json(new DataResponse(200, user || {}, "User retrieved successfully"));
  };

  getUserById = async (req: Request, res: Response) => {
    const user = await this.userService.getUserById(req.params.id);
    res
      .status(200)
      .json(new DataResponse(200, user || {}, "User retrieved successfully"));
  };

  updateUser = async (req: Request, res: Response) => {
    const updatedUser = await this.userService.updateUser(
      req.params.id,
      req.body
    );
    res
      .status(200)
      .json(
        new DataResponse(200, updatedUser || {}, "User updated successfully")
      );
  };

  deleteUser = async (req: Request, res: Response) => {
    await this.userService.deleteUser(req.params.id);
    res
      .status(200)
      .json(new DataResponse(200, {}, "User deleted successfully"));
  };
}

export default UserController;
