package raf.edu.rs.vebd6;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import raf.edu.rs.vebd6.repository.comment.CommentRepImpl;
import raf.edu.rs.vebd6.repository.comment.CommentRepository;
import raf.edu.rs.vebd6.repository.post.PostRepImpl;
import raf.edu.rs.vebd6.repository.post.PostRepository;
import raf.edu.rs.vebd6.repository.user.UserRepImpl;
import raf.edu.rs.vebd6.repository.user.UserRepository;
import raf.edu.rs.vebd6.service.CommentService;
import raf.edu.rs.vebd6.service.PostService;
import raf.edu.rs.vebd6.service.UserService;

import javax.inject.Singleton;


@ApplicationPath("/api")
public class HelloApplication extends ResourceConfig {

    public HelloApplication() {
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(PostRepImpl.class).to(PostRepository.class).in(Singleton.class);
                this.bind(CommentRepImpl.class).to(CommentRepository.class).in(Singleton.class);
                this.bind(UserRepImpl.class).to(UserRepository.class).in(Singleton.class);

                this.bindAsContract(UserService.class);
                this.bindAsContract(PostService.class);
                this.bindAsContract(CommentService.class);
            }
        };
        register(binder);

        packages("raf.edu.rs.vebd6.resources");
    }
}