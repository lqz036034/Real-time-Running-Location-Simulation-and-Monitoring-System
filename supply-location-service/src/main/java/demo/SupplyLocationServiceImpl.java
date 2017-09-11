package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupplyLocationServiceImpl implements SupplyLocationService {

    private SupplyLocationRepository repository;

    @Autowired
    public SupplyLocationServiceImpl(SupplyLocationRepository repository){
        this.repository = repository;
    }

    @Override
    public List<SupplyLocation> saveSupplyLocationsZipContains504(List<SupplyLocation> locations) {
        return (ArrayList<SupplyLocation>)this.repository.save(filterList(locations, "504"));
    }

    private List<SupplyLocation> filterList(List<SupplyLocation> listToFilter, String keyword){
        List<SupplyLocation> result = new ArrayList<>();
        for(SupplyLocation supplyLocation: listToFilter){
            if(supplyLocation.getZip().contains(keyword)){
                result.add(supplyLocation);
            }
        }
        return result;
    }
}
