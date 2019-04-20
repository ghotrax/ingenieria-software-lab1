package gt.edu.umg.ingenieria.sistemas.laboratorio1.service;

import gt.edu.umg.ingenieria.sistemas.laboratorio1.Validacion.validacio;
import gt.edu.umg.ingenieria.sistemas.laboratorio1.dao.ClientRepository;
import gt.edu.umg.ingenieria.sistemas.laboratorio1.model.Client;
import gt.edu.umg.ingenieria.sistemas.laboratorio1.model.ErrorM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Service
public class ClientService {

    private final ClientRepository clientRepository;
    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    public Client GClientByNit (String nit) {
        List<Client> clients = GTClients();
        return clients.stream()
                .filter(client -> Objects.equals(client.getNit(), nit))
                .findFirst()
                .orElse(new Client());
    }

    public List<Client> getClientsByNameAndLastName(String nameAndLastName) {
        List<Client> clients = GTClients();
        List<Client> result = new ArrayList<>();
        for (Client a: clients) {
            if(a.getFirstName().matches(nameAndLastName) || a.getLastName().matches(nameAndLastName)){
                result.add(a);
            }
        }
        return result;
    }

    public Client GClientById(long id) {
        return this.clientRepository.findById(id).get();
    }

    public List<Client> GTClients() {
        return (List<Client>) this.clientRepository.findAll();
    }

    public Object createClient(Client client) {
        String _msg = "solo puede crear clientes mayores de 18 ";
        if(!validacio.isOverOrEqualsNYears(client.getBirthDate(), 18)){
            try {
                throw new Exception(_msg);
            } catch (Exception e) {
                e.printStackTrace();
                return new ErrorM("Menor de edad", _msg);
            }
        } else if (!(client.getNit().matches("\\d+") && client.getNit().length() == 10)) {
            _msg = "NIT invalido.";
            try {
                throw new Exception(_msg);
            } catch (Exception e) {
                e.printStackTrace();
                return new ErrorM(_msg, "Nit contiene caracteres ilegales o contiene mas de 10 digitos");
            }
        } else {
            client.setFirstName(
                    client.getFirstName().substring(0, 1).toUpperCase() + client.getFirstName().substring(1).toLowerCase()
            );
            client.setLastName(
                    client.getLastName().substring(0, 1).toUpperCase() + client.getLastName().substring(1).toLowerCase()
            );
            return this.clientRepository.save(client);
        }
    }

    public Client updateClient(Client client) {
        return this.clientRepository.save(client);
    }


}
