package com.ttknpdev.springbootonetomanyh2.dao.many;

import com.ttknpdev.springbootonetomanyh2.entities.many.Address;
import com.ttknpdev.springbootonetomanyh2.entities.one.Employee;
import com.ttknpdev.springbootonetomanyh2.log.Logging;
import com.ttknpdev.springbootonetomanyh2.repositories.many.AddressRepo;
import com.ttknpdev.springbootonetomanyh2.repositories.one.EmployeeRepo;
import com.ttknpdev.springbootonetomanyh2.services.many.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressDao implements AddressService<Address> {
    private AddressRepo addressRepo;
    private EmployeeRepo employeeRepo;
    @Autowired
    public AddressDao(AddressRepo addressRepo , EmployeeRepo employeeRepo) {
        this.addressRepo = addressRepo;
        this.employeeRepo = employeeRepo;
    }
    @Override
    public Iterable<Address> reads() {
        return addressRepo.findAll();
    }

    @Override
    public Iterable<Address> deleteAllByEmployeeId(String eid) {
        try {
            List<Address> addresses = addressRepo.readsByEmployeeId(eid);
            if (addresses.get(0).getEmployee().getEid().equals(eid)) {
                Logging.addressDao.info(eid+" did exists");
                // System.out.println(eid+" exists");
                addressRepo.deleteAllByEmployeeId(eid);
            }
            return addresses;

        } catch (IndexOutOfBoundsException e) {
            Logging.addressDao.warn(eid+" didn't exist");
            throw new RuntimeException(eid+" didn't exist");
        }
    }

    @Override
    public Iterable<Address> readsByEmployeeId(String eid) {
        return addressRepo.readsByEmployeeId(eid); // native query
    }

    @Override
    public Iterable<Address> deleteByAddressId(String aid) {
        return addressRepo.findById(aid).map(address -> {
            addressRepo.delete(address);
            return addressRepo.findAll();
        }).orElseThrow(()-> {
            Logging.addressDao.warn(aid+" didn't exist");
            // System.out.println(aid+" didn't exist");
            return new RuntimeException(aid+" didn't exist");
        });
    }

    @Override
    public Address read(String aid) {
        return addressRepo.findById(aid).orElseThrow(()-> {
            Logging.addressDao.warn(aid+" didn't exist");
            // System.out.println(aid+" didn't exist");
            return new RuntimeException(aid+" didn't exist");
        });
    }

    @Override
    public Address create(Address obj, String eid) {
        return employeeRepo.findById(eid).map(employee -> {
            // my native query
            addressRepo.createToForeignKey(obj.getAid(),
                    eid,
                    obj.getCity(),
                    obj.getCountry(),
                    obj.getDetails());
            return obj;
        }).orElseThrow(()-> {
            Logging.addressDao.warn(eid+" didn't exist (can't create by native query)");
            return new RuntimeException(eid + " didn't exist");
        });
    }

    @Override
    public Address createSecond(Address obj, String eid) {
        return employeeRepo.findById(eid).map(employee -> {
            Logging.addressDao.info(eid+" did exist");
            // System.out.println(eid+" exists");
            obj.setEmployee(employee);
            return addressRepo.save(obj);
            // save() method You don't forget
            // it's looking to PK first
            // if there isn't existed
            // it will insert , but there is existed it will update
        }).orElseThrow(()-> {
            Logging.addressDao.warn(eid+" didn't exist (can't create by Jpa/Hibernate)");
            return new RuntimeException(eid + " didn't exist");
        });
    }

    @Override
    public Address createForTesting(Address obj, String eid) {
        if (eid.equals("E001")) {
            Logging.addressDao.info(eid+" did exist");
            // System.out.println("can set setEmployee(employee) method");
            return addressRepo.save(obj);
        } else {
            Logging.addressDao.warn(eid+" didn't exist (can't create by Jpa/Hibernate)");
            throw new RuntimeException(eid+" didn't exist");
        }
    }


}
