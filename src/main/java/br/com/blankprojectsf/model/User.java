package br.com.blankprojectsf.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "\"TB_USER\"")
public class User implements Serializable {
    private static final long serialVersionUID = 109890766546456L;
    
    @Id
    @Basic(optional = false)
    @Size(min = 3, max = 64, message = "ID must be between 3 and 64 characters")
    @Column(name = "\"ID\"")
    private String id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 3, max = 32, message = "first name must be between 3 and 32 characters")
    @Column(name = "\"FIRST_NAME\"")
    private String firstName;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 3, max = 32, message = "last name must be between 3 and 32 characters")
    @Column(name = "\"LAST_NAME\"")
    private String lastName;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 6, max = 32, message = "password must be between 6 and 32 characters")
    @Column(name = "\"PASSWORD\"")
    private String password;
    
    @Transient
    private String password2;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private List<UserGroup> userGroupList;
    

    public User() {
    }

    public User(String id) {
        this.id = id;
    }

    public User(String id, String firstName, String lastName, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public List<UserGroup> getUserGroupList() {
        return userGroupList;
    }

    public void setUserGroupList(List<UserGroup> userGroupList) {
        this.userGroupList = userGroupList;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jsfprohtml5.megaapp.model.User[ id=" + id + " ]";
    }
    
}
