package com.ttknpdev.springbootonetomanyh2.controller;
import com.ttknpdev.springbootonetomanyh2.entities.many.Address;
import com.ttknpdev.springbootonetomanyh2.services.many.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/address")
public class ControlPathAddress {
    private AddressService<Address> addressService;
    @Autowired
    public ControlPathAddress(AddressService<Address> addressService) {
        this.addressService = addressService;
    }
    @GetMapping(value = "/reads")
    private ResponseEntity<?> reads() {
        return ResponseEntity
                .accepted()
                .body(addressService.reads());
    }
    @GetMapping(value = "/read/{aid}")
    private ResponseEntity<?> read(@PathVariable String aid) {
        return ResponseEntity
                .accepted()
                .body(addressService.read(aid));
    }
    @GetMapping(value = "/reads/{eid}") // *** use Native Query
    private ResponseEntity<?> readsByEmployeeId(@PathVariable String eid) {
        return ResponseEntity
                .accepted()
                .body(addressService.readsByEmployeeId(eid));
    }
    @PostMapping(value = "/create/{eid}") // *** use Native Query
    private ResponseEntity<?> create(@RequestBody Address address , @PathVariable String eid) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(addressService.create(address,eid));
    }
    @PostMapping(value = "/create-second/{eid}")
    private ResponseEntity<?> createSecond(@RequestBody Address address , @PathVariable String eid) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(addressService.createSecond(address,eid));
    }
    @DeleteMapping(value = "/delete/{eid}/employee")
    private ResponseEntity<?> deleteAllByEmployeeId(@PathVariable String eid) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(addressService.deleteAllByEmployeeId(eid));
    }
}
