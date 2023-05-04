package com.josamtechie.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="IMAGE_DATA")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    @Lob
    @Column(name="ImageData",length = 1000)
    private byte[] imageData;

}
