package atesteme.controller;

import atesteme.service.AuthService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import atesteme.entity.UserEntity;
import atesteme.service.UserService;

import java.util.UUID;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @Inject
    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GET
    public Response findAll(@QueryParam("page") @DefaultValue("0") Integer page,
                            @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
        var users = userService.findAll(page, pageSize);

        return Response.ok(users).build();
    }

    @POST
    @Transactional
    public Response createUser(UserEntity userEntity) {
        return Response.ok(userService.createUser(userEntity)).build();
    }

    @POST
    @Path("/login")
    @Transactional
    public Response login(UserEntity userEntity) {
        try {
            UserEntity loggedInUser = authService.login(userEntity.getUsername(), userEntity.getPassword());
            return Response.ok(loggedInUser).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateUser(@PathParam("id") UUID userId, UserEntity userEntity) {
        return Response.ok(userService.updateUser(userId, userEntity)).build();
    }


    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") UUID userId) {
        return Response.ok(userService.findById(userId)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteById(@PathParam("id") UUID userId) {
        userService.deleteById(userId);
        return Response.ok("User deleted!").build();
    }
}
