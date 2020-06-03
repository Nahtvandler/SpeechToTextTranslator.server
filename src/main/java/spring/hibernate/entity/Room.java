package spring.hibernate.entity;

import javax.persistence.*;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @Column(name = "roomId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "roomName")
    private String name;

    @Column(name = "roomDescription")
    private String description;

    @ManyToOne
    @JoinColumn(name = "roomOwnerId")
    private Client owner;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }
}
