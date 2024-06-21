    package atesteme.repository;

    import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
    import jakarta.enterprise.context.ApplicationScoped;
    import atesteme.entity.UserEntity;

    import java.util.UUID;

    @ApplicationScoped
    public class UserRepository implements PanacheRepositoryBase<UserEntity, UUID> {

        public UserEntity findByUsername(String username) {
            return find("username", username).firstResult();
        }

    }
