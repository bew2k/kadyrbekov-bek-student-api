package kz.kadyrbekov.studentapi;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StudentController {

    private final StudentRepository repository;

    @Value("${student.name:Bek Kadyrbekov}")
    private String studentName;

    @Value("${student.group:IT2-2306}")
    private String studentGroup;

    @Value("${student.id:35534}")
    private String studentId;

    @Value("${app.version:1.0}")
    private String appVersion;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    /** Информационный endpoint: показывает имя студента и версию приложения. */
    @GetMapping("/info")
    public Map<String, String> info() {
        return Map.of(
                "application", "kadyrbekov-bek-student-api",
                "author", studentName,
                "group", studentGroup,
                "studentId", studentId,
                "version", appVersion);
    }

    // ---- CREATE ----
    @PostMapping("/students")
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.CREATED)
    public Student create(@Valid @RequestBody Student student) {
        student.setId(null);
        return repository.save(student);
    }

    // ---- READ (all) ----
    @GetMapping("/students")
    public List<Student> findAll() {
        return repository.findAll();
    }

    // ---- READ (one) ----
    @GetMapping("/students/{id}")
    public Student findOne(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
    }

    // ---- UPDATE ----
    @PutMapping("/students/{id}")
    public Student update(@PathVariable Long id, @Valid @RequestBody Student data) {
        Student existing = repository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
        existing.setName(data.getName());
        existing.setSurname(data.getSurname());
        existing.setEmail(data.getEmail());
        existing.setStudentGroup(data.getStudentGroup());
        return repository.save(existing);
    }

    // ---- DELETE ----
    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ---- Обработка ошибок ----
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<Map<String, Object>> notFound(StudentNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("status", 404, "error", "Not Found", "message", ex.getMessage()));
    }
}
