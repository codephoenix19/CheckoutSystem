package Entity;

public enum Role {
    STORE_OWNER("StoreOwner"), END_USER("EndUser");
    private String roleName;

    Role(String role){
        this.roleName = role;
    }
}
