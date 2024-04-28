package raf.edu.rs.vebd6.resources;

import jakarta.validation.Valid;
import raf.edu.rs.vebd6.entities.Comment;
import raf.edu.rs.vebd6.service.CommentService;
import raf.edu.rs.vebd6.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/comment")
public class CommentResource {
    @Inject
    private CommentService commentService;
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
        return Response.ok(commentService.getComments()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Comment create(@Valid Comment comment) {
        comment.setAuthor(userService.getUsername(getToken()));
        if (!isAuthorized()) {
            throw new ForbiddenException("Unauthorized access");
        }
        return this.commentService.addComment(comment.getPostId(),comment);
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Comment find(@PathParam("id") Integer id) {
        if (!isAuthorized()) {
            throw new ForbiddenException("Unauthorized access");
        }
        return this.commentService.findComment(id);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Integer id) {
        if (!isAuthorized()) {
            throw new ForbiddenException("Unauthorized access");
        }
        this.commentService.deleteComment(id);
    }
    private String getToken(){
        String token = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        if(token!=null) return token.split("Bearer ")[1];
        else return null;

    }

    private boolean isAuthorized() {
        String t = getToken();
        if(t!=null)return userService.isAuthorized(getToken());
        else return false;

    }
}
