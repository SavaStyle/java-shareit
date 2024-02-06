package KTE.Labs.services;

import KTE.Labs.models.Doctor;

public interface DoctorServices {
    Doctor createDoctor(Doctor doctor);

    Doctor findById(Long id);
}
