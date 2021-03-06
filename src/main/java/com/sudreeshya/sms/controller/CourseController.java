package com.sudreeshya.sms.controller;

import com.sudreeshya.sms.constant.APIPathConstants;
import com.sudreeshya.sms.dto.GenericResponse;
import com.sudreeshya.sms.request.SaveCourseRequest;
import com.sudreeshya.sms.request.UpdateCourseRequest;
import com.sudreeshya.sms.service.impl.CourseServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(APIPathConstants.COURSES)
public class CourseController {
    private final CourseServiceImpl courseServiceImpl;

    public CourseController(CourseServiceImpl courseServiceImpl) {
        this.courseServiceImpl = courseServiceImpl;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> findAllCourses() {
        GenericResponse genericResponse = courseServiceImpl.findAllCourses();
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @GetMapping(value = APIPathConstants.PathVariable.COURSEID_WRAPPER)
    public ResponseEntity<GenericResponse> findCourseById(@PathVariable(APIPathConstants.PathVariable.COURSEID) long id) {
        GenericResponse genericResponse = courseServiceImpl.findCourseById(id);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @PostMapping(value = APIPathConstants.SharedOperations.SAVE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> saveCourse(@RequestBody SaveCourseRequest request){
        GenericResponse genericResponse = courseServiceImpl.saveCourse(request);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @PostMapping(value = APIPathConstants.SharedOperations.UPDATE + "/" + APIPathConstants.PathVariable.COURSEID_WRAPPER,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> updateCourse(@PathVariable(APIPathConstants.PathVariable.COURSEID) Long id,
                                                        @RequestBody UpdateCourseRequest request){
        GenericResponse genericResponse = courseServiceImpl.updateCourse(request, id);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @GetMapping(value = APIPathConstants.SharedOperations.DELETE + "/" + APIPathConstants.PathVariable.COURSEID_WRAPPER)
    public ResponseEntity<GenericResponse> deleteCourse(@PathVariable(APIPathConstants.PathVariable.COURSEID) Long id){
        GenericResponse genericResponse = courseServiceImpl.deleteCourse(id);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @GetMapping(value = APIPathConstants.SharedOperations.TRASH)
    public ResponseEntity<GenericResponse> findDeletedCourses(){
        GenericResponse genericResponse = courseServiceImpl.findDeletedCourses();
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

}
