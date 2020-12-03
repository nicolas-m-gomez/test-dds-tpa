package dominio;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dominio.exception.EgresoInvalidoException;

//TO DO: Encontrar un mejor nombre para la clase
public class Presupuestador {
	private Proveedor proveedor;
	private DocumentoComercial documentoComercial;
	private List<ItemPresupuesto> itemsPresupuesto;

	public Presupuestador() {
		itemsPresupuesto = new ArrayList<ItemPresupuesto>();
	}

	public void cargarProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public void cargarDocumentoComercial(DocumentoComercial documentoComercial) {
		this.documentoComercial = documentoComercial;
	}

	public void cargarItemsPresupuesto(List<ItemPresupuesto> itemsPresupuesto) {
		this.itemsPresupuesto = itemsPresupuesto;
	}

	public void cargarItemPresupuesto(ItemPresupuesto itemPresupuesto) {
		this.itemsPresupuesto.add(itemPresupuesto);
	}

	public void generarPresupuesto(Egreso egreso) {
		Objects.requireNonNull(egreso, "El presupuesto debe estar asignado a un Egreso");
		validarItemsCantidad(egreso.getItems(), itemsPresupuesto);
		validarItemsDetalle(egreso.getItems(), itemsPresupuesto);
		egreso.agregarPresupuesto(this.crearPresupuesto());
	}

	private Presupuesto crearPresupuesto() {

		return new Presupuesto(this.proveedor, this.documentoComercial, this.itemsPresupuesto);
	}

	public void validarItemsCantidad(List<ItemEgreso> itemsEgreso, List<ItemPresupuesto> itemsPresupuesto) {
		if (itemsPresupuesto.size() != itemsEgreso.size()) {
			throw new EgresoInvalidoException("El presupuesto no corresponde al egreso indicado.");
		}

	}

	public void validarItemsDetalle(List<ItemEgreso> itemsEgreso, List<ItemPresupuesto> itemsPresupuesto) {

		if (!itemsPresupuesto.stream().anyMatch(itemPresupuesto -> itemsEgreso.contains(itemPresupuesto))) {
			throw new EgresoInvalidoException("El presupuesto no corresponde al egreso indicado.");
		}
	}

}
