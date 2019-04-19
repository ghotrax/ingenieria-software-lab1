package gt.edu.umg.ingenieria.sistemas.laboratorio1.controller;

import gt.edu.umg.ingenieria.sistemas.laboratorio1.model.Client;
import gt.edu.umg.ingenieria.sistemas.laboratorio1.service.ClientService;
import gt.edu.umg.ingenieria.sistemas.laboratorio1.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author Josué Barillas (jbarillas)
 */
@RestController
@RequestMapping("/cliente")
public class ClientController {


    private final ReportService _reportService;
    private final ClientService _clientService;

    @Autowired
    public ClientController(ReportService reportService, ClientService clientService) {
        _reportService = reportService;
        _clientService = clientService;
    }
    @GetMapping("/buscarpoId")
    public Client GBId(@RequestParam(name = "id") long id) {
        return this._clientService.GClientById(id);
    }

    @GetMapping("/buscarPorNit")
    public Client GBNit(@RequestParam(name = "nit") String nit) {
        return this._clientService.GClientByNit(nit);
    }

    @GetMapping("/buscarPorNombreApellido")
    public List<Client> GBNameAndLastName(@RequestParam(name = "query") String nameAndLastName) {
        return this._clientService.getClientsByNameAndLastName(nameAndLastName);
    }

    @GetMapping("/buscarTodos")
    private List<Client> getByAll() {
        return this._clientService.GTClients();
    }

    @PostMapping("/crearCliente")
    public Object create(@RequestBody Client client) {
        return this._clientService.createClient(client);
    }

    @PutMapping("/editarCliente/{id}/{nit}")
    public Client upteNit(@PathVariable long id, @PathVariable String nit) {
        Client client = GBId(id);
        client.setNit(nit);
        return this._clientService.updateClient(client);
    }

    @PutMapping("/editarCliente/{id}/{name}/{lastName}")
    public Client upteNameAndLastName(@PathVariable long id, @PathVariable String name, @PathVariable String lastName) {
        Client client = GBId(id);
        client.setFirstName(name);
        client.setLastName(lastName);
        return this._clientService.updateClient(client);
    }

    @GetMapping("/generarReporteClientes")
    public String generaReport() {
        return this._reportService.generaRepo();
    }



}
