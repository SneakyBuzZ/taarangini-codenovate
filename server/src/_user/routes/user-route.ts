import { Router } from "express";
import { validateData } from "@/middlewares/validate-middleware";
import { catchAsync } from "@/utils/catch-async";
import { UserController } from "@/_user/controllers/user-controller";
import { authenticateJwt } from "@/middlewares/authenticate-middleware";
import { RegisterUserDTO } from "@/_user/dto";

const userRouter = Router();
const userController = new UserController();

userRouter.post(
  "/",
  validateData(RegisterUserDTO),
  catchAsync(userController.register)
);

userRouter.get("/", authenticateJwt(), catchAsync(userController.getUser));

userRouter.post(
  "/auth",
  authenticateJwt(),
  catchAsync(userController.getAuthStatus)
);

userRouter.get(
  "/all",
  authenticateJwt(),
  catchAsync(userController.getAllUsers)
);

userRouter.get(
  "/:id",
  authenticateJwt(),
  catchAsync(userController.getUserById)
);

userRouter.put(
  "/:id",
  authenticateJwt(),
  catchAsync(userController.updateUser)
);

userRouter.delete(
  "/:id",
  authenticateJwt(),
  catchAsync(userController.deleteUser)
);

export default userRouter;
