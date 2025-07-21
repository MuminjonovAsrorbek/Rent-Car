package uz.dev.rentcar.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Created by: asrorbek
 * DateTime: 7/20/25 15:36
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "attachments")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String path;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String originalName;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private Long size;

    private boolean isPrimary = false;

    @ManyToOne
    private Car car;
}
