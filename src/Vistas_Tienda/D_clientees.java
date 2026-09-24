
package Vistas_Tienda;


public class D_clientees {
    
    private int IDcliente;
    private String dni;
    private String nombre;
    private String telefono;

    public D_clientees() {
    }

    public D_clientees(int IDcliente, String dni, String nombre, String telefono) {
        this.IDcliente = IDcliente;
        this.dni = dni;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public int getIDcliente() {
        return IDcliente;
    }

    public void setIDcliente(int IDcliente) {
        this.IDcliente = IDcliente;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    
    
    
    
}
