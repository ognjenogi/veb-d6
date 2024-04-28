package raf.edu.rs.vebd6.resources;


import jakarta.validation.Valid;
import raf.edu.rs.vebd6.entities.Post;
import raf.edu.rs.vebd6.service.PostService;
import raf.edu.rs.vebd6.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;


import javax.ws.rs.core.Response;

@Path("/posts")
public class PostResource {
    @Inject
    private PostService postService;

    @Context
    private HttpHeaders headers;
    @Inject
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all() {
        if (!isAuthorized()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        return Response.ok(this.postService.getPosts()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Post create(@Valid Post post) {
        post.setAuthor(userService.getUsername(getToken()));
        if (!isAuthorized()) {
            throw new ForbiddenException("Unauthorized access");
        }

        return this.postService.addPost(post);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Post find(@PathParam("id") Integer id) {
        if (!isAuthorized()) {
            throw new ForbiddenException("Unauthorized access");
        }

        return this.postService.findPost(id);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Integer id) {
        if (!isAuthorized()) {
            throw new ForbiddenException("Unauthorized access");
        }

        this.postService.deletePost(id);
    }
    private String getToken(){
        String token = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        return token.split("Bearer ")[1];
    }

    private boolean isAuthorized() {
        return userService.isAuthorized(getToken());
    }
}
