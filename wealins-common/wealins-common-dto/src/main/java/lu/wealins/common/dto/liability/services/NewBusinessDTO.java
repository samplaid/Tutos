/**
 ! This is the interface with webia application form data to create a new policy
 ! 
 ! NewBusinessDTO is a copy of the class NewBusinessDTO generated from the wsdl file from Lissa web service
 ! To update this file, just copy NewBusinessDTO.java and rename to NewBusinessDTO.
 ! 
 */
package lu.wealins.common.dto.liability.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NewBusinessDTO", propOrder = {
    "impUseridUsers",
    "impGrpControls",
    "impGrpCpr",
    "impGrpPas",
    "impCallMethodCommunications",
    "impGrpPbc",
    "impGrpCvg",
    "impGrpPfd",
    "impPolicyPolicies",
    "impPonGeneralNotes",
    "impValidationUsers"
})
public class NewBusinessDTO {

	@XmlElement(name = "ImpUseridUsers")
	protected NewBusinessDTO.ImpUseridUsers impUseridUsers;
	@XmlElement(name = "ImpGrpControls")
	protected NewBusinessDTO.ImpGrpControls impGrpControls;
	@XmlElement(name = "ImpGrpCpr")
	protected NewBusinessDTO.ImpGrpCpr impGrpCpr;
	@XmlElement(name = "ImpGrpPas")
	protected NewBusinessDTO.ImpGrpPas impGrpPas;
	@XmlElement(name = "ImpCallMethodCommunications")
	protected NewBusinessDTO.ImpCallMethodCommunications impCallMethodCommunications;
	@XmlElement(name = "ImpGrpPbc")
	protected NewBusinessDTO.ImpGrpPbc impGrpPbc;
	@XmlElement(name = "ImpGrpCvg")
	protected NewBusinessDTO.ImpGrpCvg impGrpCvg;
	@XmlElement(name = "ImpGrpPfd")
	protected NewBusinessDTO.ImpGrpPfd impGrpPfd;
	@XmlElement(name = "ImpPolicyPolicies")
	protected NewBusinessDTO.ImpPolicyPolicies impPolicyPolicies;
	@XmlElement(name = "ImpPonGeneralNotes")
	protected NewBusinessDTO.ImpPonGeneralNotes impPonGeneralNotes;
	@XmlElement(name = "ImpValidationUsers")
	protected NewBusinessDTO.ImpValidationUsers impValidationUsers;
	@XmlAttribute(name = "command")
	protected String command;
	@XmlAttribute(name = "clientId")
	protected String clientId;
	@XmlAttribute(name = "clientPassword")
	protected String clientPassword;
	@XmlAttribute(name = "nextLocation")
	protected String nextLocation;
	@XmlAttribute(name = "exitState", required = true)
	protected int exitState;
	@XmlAttribute(name = "dialect")
	protected String dialect;

	// ADDED PROPERTIES
	private Collection<PolicyTransferDTO> policyTransfers = new ArrayList<>();
	private BigDecimal entryFeesPct;
	private BigDecimal mngtFeesPct;

	/**
	 * Gets the value of the impUseridUsers property.
	 * 
	 * @return possible object is {@link NewBusinessDTO.ImpUseridUsers }
	 * 
	 */
	public NewBusinessDTO.ImpUseridUsers getImpUseridUsers() {
		return impUseridUsers;
	}

	/**
	 * Sets the value of the impUseridUsers property.
	 * 
	 * @param value allowed object is {@link NewBusinessDTO.ImpUseridUsers }
	 * 
	 */
	public void setImpUseridUsers(NewBusinessDTO.ImpUseridUsers value) {
		this.impUseridUsers = value;
	}

	/**
	 * Gets the value of the impGrpControls property.
	 * 
	 * @return possible object is {@link NewBusinessDTO.ImpGrpControls }
	 * 
	 */
	public NewBusinessDTO.ImpGrpControls getImpGrpControls() {
		return impGrpControls;
	}

	/**
	 * Sets the value of the impGrpControls property.
	 * 
	 * @param value allowed object is {@link NewBusinessDTO.ImpGrpControls }
	 * 
	 */
	public void setImpGrpControls(NewBusinessDTO.ImpGrpControls value) {
		this.impGrpControls = value;
	}

	/**
	 * Gets the value of the impGrpCpr property.
	 * 
	 * @return possible object is {@link NewBusinessDTO.ImpGrpCpr }
	 * 
	 */
	public NewBusinessDTO.ImpGrpCpr getImpGrpCpr() {
		return impGrpCpr;
	}

	/**
	 * Sets the value of the impGrpCpr property.
	 * 
	 * @param value allowed object is {@link NewBusinessDTO.ImpGrpCpr }
	 * 
	 */
	public void setImpGrpCpr(NewBusinessDTO.ImpGrpCpr value) {
		this.impGrpCpr = value;
	}

	/**
	 * Gets the value of the impGrpPas property.
	 * 
	 * @return possible object is {@link NewBusinessDTO.ImpGrpPas }
	 * 
	 */
	public NewBusinessDTO.ImpGrpPas getImpGrpPas() {
		return impGrpPas;
	}

	/**
	 * Sets the value of the impGrpPas property.
	 * 
	 * @param value allowed object is {@link NewBusinessDTO.ImpGrpPas }
	 * 
	 */
	public void setImpGrpPas(NewBusinessDTO.ImpGrpPas value) {
		this.impGrpPas = value;
	}

	/**
	 * Gets the value of the impCallMethodCommunications property.
	 * 
	 * @return possible object is {@link NewBusinessDTO.ImpCallMethodCommunications }
	 * 
	 */
	public NewBusinessDTO.ImpCallMethodCommunications getImpCallMethodCommunications() {
		return impCallMethodCommunications;
	}

	/**
	 * Sets the value of the impCallMethodCommunications property.
	 * 
	 * @param value allowed object is {@link NewBusinessDTO.ImpCallMethodCommunications }
	 * 
	 */
	public void setImpCallMethodCommunications(NewBusinessDTO.ImpCallMethodCommunications value) {
		this.impCallMethodCommunications = value;
	}

	/**
	 * Gets the value of the impGrpPbc property.
	 * 
	 * @return possible object is {@link NewBusinessDTO.ImpGrpPbc }
	 * 
	 */
	public NewBusinessDTO.ImpGrpPbc getImpGrpPbc() {
		return impGrpPbc;
	}

	/**
	 * Sets the value of the impGrpPbc property.
	 * 
	 * @param value allowed object is {@link NewBusinessDTO.ImpGrpPbc }
	 * 
	 */
	public void setImpGrpPbc(NewBusinessDTO.ImpGrpPbc value) {
		this.impGrpPbc = value;
	}

	/**
	 * Gets the value of the impGrpCvg property.
	 * 
	 * @return possible object is {@link NewBusinessDTO.ImpGrpCvg }
	 * 
	 */
	public NewBusinessDTO.ImpGrpCvg getImpGrpCvg() {
		return impGrpCvg;
	}

	/**
	 * Sets the value of the impGrpCvg property.
	 * 
	 * @param value allowed object is {@link NewBusinessDTO.ImpGrpCvg }
	 * 
	 */
	public void setImpGrpCvg(NewBusinessDTO.ImpGrpCvg value) {
		this.impGrpCvg = value;
	}

	/**
	 * Gets the value of the impGrpPfd property.
	 * 
	 * @return possible object is {@link NewBusinessDTO.ImpGrpPfd }
	 * 
	 */
	public NewBusinessDTO.ImpGrpPfd getImpGrpPfd() {
		return impGrpPfd;
	}

	/**
	 * Sets the value of the impGrpPfd property.
	 * 
	 * @param value allowed object is {@link NewBusinessDTO.ImpGrpPfd }
	 * 
	 */
	public void setImpGrpPfd(NewBusinessDTO.ImpGrpPfd value) {
		this.impGrpPfd = value;
	}

	/**
	 * Gets the value of the impPolicyPolicies property.
	 * 
	 * @return possible object is {@link NewBusinessDTO.ImpPolicyPolicies }
	 * 
	 */
	public NewBusinessDTO.ImpPolicyPolicies getImpPolicyPolicies() {
		return impPolicyPolicies;
	}

	/**
	 * Sets the value of the impPolicyPolicies property.
	 * 
	 * @param value allowed object is {@link NewBusinessDTO.ImpPolicyPolicies }
	 * 
	 */
	public void setImpPolicyPolicies(NewBusinessDTO.ImpPolicyPolicies value) {
		this.impPolicyPolicies = value;
	}

	/**
	 * Gets the value of the impPonGeneralNotes property.
	 * 
	 * @return possible object is {@link NewBusinessDTO.ImpPonGeneralNotes }
	 * 
	 */
	public NewBusinessDTO.ImpPonGeneralNotes getImpPonGeneralNotes() {
		return impPonGeneralNotes;
	}

	/**
	 * Sets the value of the impPonGeneralNotes property.
	 * 
	 * @param value allowed object is {@link NewBusinessDTO.ImpPonGeneralNotes }
	 * 
	 */
	public void setImpPonGeneralNotes(NewBusinessDTO.ImpPonGeneralNotes value) {
		this.impPonGeneralNotes = value;
	}

	/**
	 * Gets the value of the impValidationUsers property.
	 * 
	 * @return possible object is {@link NewBusinessDTO.ImpValidationUsers }
	 * 
	 */
	public NewBusinessDTO.ImpValidationUsers getImpValidationUsers() {
		return impValidationUsers;
	}

	/**
	 * Sets the value of the impValidationUsers property.
	 * 
	 * @param value allowed object is {@link NewBusinessDTO.ImpValidationUsers }
	 * 
	 */
	public void setImpValidationUsers(NewBusinessDTO.ImpValidationUsers value) {
		this.impValidationUsers = value;
	}

	/**
	 * Gets the value of the command property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * Sets the value of the command property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setCommand(String value) {
		this.command = value;
	}

	/**
	 * Gets the value of the clientId property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * Sets the value of the clientId property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setClientId(String value) {
		this.clientId = value;
	}

	/**
	 * Gets the value of the clientPassword property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getClientPassword() {
		return clientPassword;
	}

	/**
	 * Sets the value of the clientPassword property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setClientPassword(String value) {
		this.clientPassword = value;
	}

	/**
	 * Gets the value of the nextLocation property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getNextLocation() {
		return nextLocation;
	}

	/**
	 * Sets the value of the nextLocation property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setNextLocation(String value) {
		this.nextLocation = value;
	}

	/**
	 * Gets the value of the exitState property.
	 * 
	 */
	public int getExitState() {
		return exitState;
	}

	/**
	 * Sets the value of the exitState property.
	 * 
	 */
	public void setExitState(int value) {
		this.exitState = value;
	}

	/**
	 * Gets the value of the dialect property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDialect() {
		return dialect;
	}

	/**
	 * Sets the value of the dialect property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setDialect(String value) {
		this.dialect = value;
	}


	public Collection<PolicyTransferDTO> getPolicyTransfers() {
		return policyTransfers;
	}

	public void setPolicyTransfers(Collection<PolicyTransferDTO> policyTransfers) {
		this.policyTransfers = policyTransfers;
	}

	/**
	 * @return the entryFeesPct
	 */
	public BigDecimal getEntryFeesPct() {
		return entryFeesPct;
	}

	/**
	 * @param entryFeesPct the entryFeesPct to set
	 */
	public void setEntryFeesPct(BigDecimal entryFeesPct) {
		this.entryFeesPct = entryFeesPct;
	}

	/**
	 * @return the mngtFeesPct
	 */
	public BigDecimal getMngtFeesPct() {
		return mngtFeesPct;
	}

	/**
	 * @param mngtFeesPct the mngtFeesPct to set
	 */
	public void setMngtFeesPct(BigDecimal mngtFeesPct) {
		this.mngtFeesPct = mngtFeesPct;
	}

	/**
	 * <p>
	 * Java class for anonymous complex type.
	 * 
	 * <p>
	 * The following schema fragment specifies the expected content contained within this class.
	 * 
	 * <pre>
	 * &lt;complexType&gt;
	 *   &lt;complexContent&gt;
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *       &lt;sequence&gt;
	 *         &lt;element name="CallFunction" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *       &lt;/sequence&gt;
	 *     &lt;/restriction&gt;
	 *   &lt;/complexContent&gt;
	 * &lt;/complexType&gt;
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
			"callFunction"
	})
	public static class ImpCallMethodCommunications {

		@XmlElement(name = "CallFunction", required = true)
		protected String callFunction;

		/**
		 * Gets the value of the callFunction property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getCallFunction() {
			return callFunction;
		}

		/**
		 * Sets the value of the callFunction property.
		 * 
		 * @param value allowed object is {@link String }
		 * 
		 */
		public void setCallFunction(String value) {
			this.callFunction = value;
		}

	}


	/**
	 * <p>
	 * Java class for anonymous complex type.
	 * 
	 * <p>
	 * The following schema fragment specifies the expected content contained within this class.
	 * 
	 * <pre>
	 * &lt;complexType&gt;
	 *   &lt;complexContent&gt;
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *       &lt;sequence&gt;
	 *         &lt;element name="row" maxOccurs="unbounded" minOccurs="0"&gt;
	 *           &lt;complexType&gt;
	 *             &lt;complexContent&gt;
	 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *                 &lt;sequence&gt;
	 *                   &lt;element name="ImpItmControlProductValues" minOccurs="0"&gt;
	 *                     &lt;complexType&gt;
	 *                       &lt;complexContent&gt;
	 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *                           &lt;sequence&gt;
	 *                             &lt;element name="Control" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                             &lt;element name="NumericValue" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                             &lt;element name="ProductLine" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                           &lt;/sequence&gt;
	 *                         &lt;/restriction&gt;
	 *                       &lt;/complexContent&gt;
	 *                     &lt;/complexType&gt;
	 *                   &lt;/element&gt;
	 *                 &lt;/sequence&gt;
	 *               &lt;/restriction&gt;
	 *             &lt;/complexContent&gt;
	 *           &lt;/complexType&gt;
	 *         &lt;/element&gt;
	 *       &lt;/sequence&gt;
	 *     &lt;/restriction&gt;
	 *   &lt;/complexContent&gt;
	 * &lt;/complexType&gt;
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
			"rows"
	})
	public static class ImpGrpControls {

		@XmlElement(name = "row")
		protected List<NewBusinessDTO.ImpGrpControls.Row> rows;

		/**
		 * Gets the value of the rows property.
		 * 
		 * <p>
		 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there
		 * is not a <CODE>set</CODE> method for the rows property.
		 * 
		 * <p>
		 * For example, to add a new item, do as follows:
		 * 
		 * <pre>
		 * getRows().add(newItem);
		 * </pre>
		 * 
		 * 
		 * <p>
		 * Objects of the following type(s) are allowed in the list {@link NewBusinessDTO.ImpGrpControls.Row }
		 * 
		 * 
		 */
		public List<NewBusinessDTO.ImpGrpControls.Row> getRows() {
			if (rows == null) {
				rows = new ArrayList<NewBusinessDTO.ImpGrpControls.Row>();
			}
			return this.rows;
		}


		/**
		 * <p>
		 * Java class for anonymous complex type.
		 * 
		 * <p>
		 * The following schema fragment specifies the expected content contained within this class.
		 * 
		 * <pre>
		 * &lt;complexType&gt;
		 *   &lt;complexContent&gt;
		 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
		 *       &lt;sequence&gt;
		 *         &lt;element name="ImpItmControlProductValues" minOccurs="0"&gt;
		 *           &lt;complexType&gt;
		 *             &lt;complexContent&gt;
		 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
		 *                 &lt;sequence&gt;
		 *                   &lt;element name="Control" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                   &lt;element name="NumericValue" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                   &lt;element name="ProductLine" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                 &lt;/sequence&gt;
		 *               &lt;/restriction&gt;
		 *             &lt;/complexContent&gt;
		 *           &lt;/complexType&gt;
		 *         &lt;/element&gt;
		 *       &lt;/sequence&gt;
		 *     &lt;/restriction&gt;
		 *   &lt;/complexContent&gt;
		 * &lt;/complexType&gt;
		 * </pre>
		 * 
		 * 
		 */
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = {
				"impItmControlProductValues"
		})
		public static class Row {

			@XmlElement(name = "ImpItmControlProductValues")
			protected NewBusinessDTO.ImpGrpControls.Row.ImpItmControlProductValues impItmControlProductValues;

			/**
			 * Gets the value of the impItmControlProductValues property.
			 * 
			 * @return possible object is {@link NewBusinessDTO.ImpGrpControls.Row.ImpItmControlProductValues }
			 * 
			 */
			public NewBusinessDTO.ImpGrpControls.Row.ImpItmControlProductValues getImpItmControlProductValues() {
				return impItmControlProductValues;
			}

			/**
			 * Sets the value of the impItmControlProductValues property.
			 * 
			 * @param value allowed object is {@link NewBusinessDTO.ImpGrpControls.Row.ImpItmControlProductValues }
			 * 
			 */
			public void setImpItmControlProductValues(NewBusinessDTO.ImpGrpControls.Row.ImpItmControlProductValues value) {
				this.impItmControlProductValues = value;
			}


			/**
			 * <p>
			 * Java class for anonymous complex type.
			 * 
			 * <p>
			 * The following schema fragment specifies the expected content contained within this class.
			 * 
			 * <pre>
			 * &lt;complexType&gt;
			 *   &lt;complexContent&gt;
			 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
			 *       &lt;sequence&gt;
			 *         &lt;element name="Control" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *         &lt;element name="NumericValue" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *         &lt;element name="ProductLine" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *       &lt;/sequence&gt;
			 *     &lt;/restriction&gt;
			 *   &lt;/complexContent&gt;
			 * &lt;/complexType&gt;
			 * </pre>
			 * 
			 * 
			 */
			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = {
					"control",
					"numericValue",
					"productLine"
			})
			public static class ImpItmControlProductValues {

				@XmlElement(name = "Control", required = true)
				protected String control;
				@XmlElement(name = "NumericValue", required = true)
				protected String numericValue;
				@XmlElement(name = "ProductLine", required = true)
				protected String productLine;

				/**
				 * Gets the value of the control property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getControl() {
					return control;
				}

				/**
				 * Sets the value of the control property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setControl(String value) {
					this.control = value;
				}

				/**
				 * Gets the value of the numericValue property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getNumericValue() {
					return numericValue;
				}

				/**
				 * Sets the value of the numericValue property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setNumericValue(String value) {
					this.numericValue = value;
				}

				/**
				 * Gets the value of the productLine property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getProductLine() {
					return productLine;
				}

				/**
				 * Sets the value of the productLine property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setProductLine(String value) {
					this.productLine = value;
				}

			}

		}

	}


	/**
	 * <p>
	 * Java class for anonymous complex type.
	 * 
	 * <p>
	 * The following schema fragment specifies the expected content contained within this class.
	 * 
	 * <pre>
	 * &lt;complexType&gt;
	 *   &lt;complexContent&gt;
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *       &lt;sequence&gt;
	 *         &lt;element name="row" maxOccurs="unbounded" minOccurs="0"&gt;
	 *           &lt;complexType&gt;
	 *             &lt;complexContent&gt;
	 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *                 &lt;sequence&gt;
	 *                   &lt;element name="ImpItmCprCliPolRelationships" minOccurs="0"&gt;
	 *                     &lt;complexType&gt;
	 *                       &lt;complexContent&gt;
	 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *                           &lt;sequence&gt;
	 *                             &lt;element name="TypeNumber" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
	 *                             &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                             &lt;element name="ActiveDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                             &lt;element name="PercentageSplit" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                             &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
	 *                             &lt;element name="Client" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
	 *                           &lt;/sequence&gt;
	 *                         &lt;/restriction&gt;
	 *                       &lt;/complexContent&gt;
	 *                     &lt;/complexType&gt;
	 *                   &lt;/element&gt;
	 *                 &lt;/sequence&gt;
	 *               &lt;/restriction&gt;
	 *             &lt;/complexContent&gt;
	 *           &lt;/complexType&gt;
	 *         &lt;/element&gt;
	 *       &lt;/sequence&gt;
	 *     &lt;/restriction&gt;
	 *   &lt;/complexContent&gt;
	 * &lt;/complexType&gt;
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
			"rows"
	})
	public static class ImpGrpCpr {

		@XmlElement(name = "row")
		protected List<NewBusinessDTO.ImpGrpCpr.Row> rows;

		/**
		 * Gets the value of the rows property.
		 * 
		 * <p>
		 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there
		 * is not a <CODE>set</CODE> method for the rows property.
		 * 
		 * <p>
		 * For example, to add a new item, do as follows:
		 * 
		 * <pre>
		 * getRows().add(newItem);
		 * </pre>
		 * 
		 * 
		 * <p>
		 * Objects of the following type(s) are allowed in the list {@link NewBusinessDTO.ImpGrpCpr.Row }
		 * 
		 * 
		 */
		public List<NewBusinessDTO.ImpGrpCpr.Row> getRows() {
			if (rows == null) {
				rows = new ArrayList<NewBusinessDTO.ImpGrpCpr.Row>();
			}
			return this.rows;
		}


		/**
		 * <p>
		 * Java class for anonymous complex type.
		 * 
		 * <p>
		 * The following schema fragment specifies the expected content contained within this class.
		 * 
		 * <pre>
		 * &lt;complexType&gt;
		 *   &lt;complexContent&gt;
		 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
		 *       &lt;sequence&gt;
		 *         &lt;element name="ImpItmCprCliPolRelationships" minOccurs="0"&gt;
		 *           &lt;complexType&gt;
		 *             &lt;complexContent&gt;
		 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
		 *                 &lt;sequence&gt;
		 *                   &lt;element name="TypeNumber" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
		 *                   &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                   &lt;element name="ActiveDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                   &lt;element name="PercentageSplit" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                   &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
		 *                   &lt;element name="Client" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
		 *                 &lt;/sequence&gt;
		 *               &lt;/restriction&gt;
		 *             &lt;/complexContent&gt;
		 *           &lt;/complexType&gt;
		 *         &lt;/element&gt;
		 *       &lt;/sequence&gt;
		 *     &lt;/restriction&gt;
		 *   &lt;/complexContent&gt;
		 * &lt;/complexType&gt;
		 * </pre>
		 * 
		 * 
		 */
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = {
				"impItmCprCliPolRelationships"
		})
		public static class Row {

			@XmlElement(name = "ImpItmCprCliPolRelationships")
			protected NewBusinessDTO.ImpGrpCpr.Row.ImpItmCprCliPolRelationships impItmCprCliPolRelationships;

			/**
			 * Gets the value of the impItmCprCliPolRelationships property.
			 * 
			 * @return possible object is {@link NewBusinessDTO.ImpGrpCpr.Row.ImpItmCprCliPolRelationships }
			 * 
			 */
			public NewBusinessDTO.ImpGrpCpr.Row.ImpItmCprCliPolRelationships getImpItmCprCliPolRelationships() {
				return impItmCprCliPolRelationships;
			}

			/**
			 * Sets the value of the impItmCprCliPolRelationships property.
			 * 
			 * @param value allowed object is {@link NewBusinessDTO.ImpGrpCpr.Row.ImpItmCprCliPolRelationships }
			 * 
			 */
			public void setImpItmCprCliPolRelationships(NewBusinessDTO.ImpGrpCpr.Row.ImpItmCprCliPolRelationships value) {
				this.impItmCprCliPolRelationships = value;
			}


			/**
			 * <p>
			 * Java class for anonymous complex type.
			 * 
			 * <p>
			 * The following schema fragment specifies the expected content contained within this class.
			 * 
			 * <pre>
			 * &lt;complexType&gt;
			 *   &lt;complexContent&gt;
			 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
			 *       &lt;sequence&gt;
			 *         &lt;element name="TypeNumber" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
			 *         &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *         &lt;element name="ActiveDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *         &lt;element name="PercentageSplit" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
			 *         &lt;element name="Client" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
			 *       &lt;/sequence&gt;
			 *     &lt;/restriction&gt;
			 *   &lt;/complexContent&gt;
			 * &lt;/complexType&gt;
			 * </pre>
			 * 
			 * 
			 */
			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = {
					"typeNumber",
					"endDate",
					"activeDate",
					"percentageSplit",
					"type",
					"client",
					"exEqualParts"
			})
			public static class ImpItmCprCliPolRelationships {

				@XmlElement(name = "TypeNumber")
				protected short typeNumber;
				@XmlElement(name = "EndDate", required = true)
				protected String endDate;
				@XmlElement(name = "ActiveDate", required = true)
				protected String activeDate;
				@XmlElement(name = "PercentageSplit", required = true)
				protected String percentageSplit;
				@XmlElement(name = "Type")
				protected short type;
				@XmlElement(name = "Client")
				protected int client;
				@XmlElement(name = "ExEqualParts", required = true)
				protected String exEqualParts;

				/**
				 * Gets the value of the typeNumber property.
				 * 
				 */
				public short getTypeNumber() {
					return typeNumber;
				}

				/**
				 * Sets the value of the typeNumber property.
				 * 
				 */
				public void setTypeNumber(short value) {
					this.typeNumber = value;
				}

				/**
				 * Gets the value of the endDate property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getEndDate() {
					return endDate;
				}

				/**
				 * Sets the value of the endDate property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setEndDate(String value) {
					this.endDate = value;
				}

				/**
				 * Gets the value of the activeDate property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getActiveDate() {
					return activeDate;
				}

				/**
				 * Sets the value of the activeDate property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setActiveDate(String value) {
					this.activeDate = value;
				}

				/**
				 * Gets the value of the percentageSplit property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getPercentageSplit() {
					return percentageSplit;
				}

				/**
				 * Sets the value of the percentageSplit property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setPercentageSplit(String value) {
					this.percentageSplit = value;
				}

				/**
				 * Gets the value of the type property.
				 * 
				 */
				public short getType() {
					return type;
				}

				/**
				 * Sets the value of the type property.
				 * 
				 */
				public void setType(short value) {
					this.type = value;
				}

				/**
				 * Gets the value of the client property.
				 * 
				 */
				public int getClient() {
					return client;
				}

				/**
				 * Sets the value of the client property.
				 * 
				 */
				public void setClient(int value) {
					this.client = value;
				}

				/**
				 * Gets the value of the exEqualParts property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getExEqualParts() {
					return exEqualParts;
				}

				/**
				 * Sets the value of the exEqualParts property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setExEqualParts(String value) {
					this.exEqualParts = value;
				}

			}

		}

	}


	/**
	 * <p>
	 * Java class for anonymous complex type.
	 * 
	 * <p>
	 * The following schema fragment specifies the expected content contained within this class.
	 * 
	 * <pre>
	 * &lt;complexType&gt;
	 *   &lt;complexContent&gt;
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *       &lt;sequence&gt;
	 *         &lt;element name="row" maxOccurs="unbounded" minOccurs="0"&gt;
	 *           &lt;complexType&gt;
	 *             &lt;complexContent&gt;
	 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *                 &lt;sequence&gt;
	 *                   &lt;element name="ImpItmPclAssured2PolicyCvgLives" minOccurs="0"&gt;
	 *                     &lt;complexType&gt;
	 *                       &lt;complexContent&gt;
	 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *                           &lt;sequence&gt;
	 *                             &lt;element name="Client" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
	 *                             &lt;element name="AgeRating" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
	 *                             &lt;element name="AddnFactor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                             &lt;element name="AddnRpm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                           &lt;/sequence&gt;
	 *                         &lt;/restriction&gt;
	 *                       &lt;/complexContent&gt;
	 *                     &lt;/complexType&gt;
	 *                   &lt;/element&gt;
	 *                   &lt;element name="ImpItmPclAssured1PolicyCvgLives" minOccurs="0"&gt;
	 *                     &lt;complexType&gt;
	 *                       &lt;complexContent&gt;
	 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *                           &lt;sequence&gt;
	 *                             &lt;element name="Client" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
	 *                             &lt;element name="AgeRating" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
	 *                             &lt;element name="AddnFactor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                             &lt;element name="AddnRpm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                           &lt;/sequence&gt;
	 *                         &lt;/restriction&gt;
	 *                       &lt;/complexContent&gt;
	 *                     &lt;/complexType&gt;
	 *                   &lt;/element&gt;
	 *                   &lt;element name="ImpItmPcpPolicyPremiums" minOccurs="0"&gt;
	 *                     &lt;complexType&gt;
	 *                       &lt;complexContent&gt;
	 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *                           &lt;sequence&gt;
	 *                             &lt;element name="ModalPremium" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                             &lt;element name="Currency" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                             &lt;element name="Frequency" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
	 *                           &lt;/sequence&gt;
	 *                         &lt;/restriction&gt;
	 *                       &lt;/complexContent&gt;
	 *                     &lt;/complexType&gt;
	 *                   &lt;/element&gt;
	 *                   &lt;element name="ImpItmPocPolicyCoverages" minOccurs="0"&gt;
	 *                     &lt;complexType&gt;
	 *                       &lt;complexContent&gt;
	 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *                           &lt;sequence&gt;
	 *                             &lt;element name="ProductLine" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                             &lt;element name="Term" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                             &lt;element name="Currency" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                             &lt;element name="InitialSumAssured" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                             &lt;element name="Coverage" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
	 *                             &lt;element name="SoldBasis" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
	 *                             &lt;element name="Multiplier" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                             &lt;element name="LivesType" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
	 *                             &lt;element name="DateOfApplication" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                             &lt;element name="DateOfReqCommencement" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                             &lt;element name="DateCommenced" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                           &lt;/sequence&gt;
	 *                         &lt;/restriction&gt;
	 *                       &lt;/complexContent&gt;
	 *                     &lt;/complexType&gt;
	 *                   &lt;/element&gt;
	 *                 &lt;/sequence&gt;
	 *               &lt;/restriction&gt;
	 *             &lt;/complexContent&gt;
	 *           &lt;/complexType&gt;
	 *         &lt;/element&gt;
	 *       &lt;/sequence&gt;
	 *     &lt;/restriction&gt;
	 *   &lt;/complexContent&gt;
	 * &lt;/complexType&gt;
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
			"rows"
	})
	public static class ImpGrpCvg {

		@XmlElement(name = "row")
		protected List<NewBusinessDTO.ImpGrpCvg.Row> rows;

		/**
		 * Gets the value of the rows property.
		 * 
		 * <p>
		 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there
		 * is not a <CODE>set</CODE> method for the rows property.
		 * 
		 * <p>
		 * For example, to add a new item, do as follows:
		 * 
		 * <pre>
		 * getRows().add(newItem);
		 * </pre>
		 * 
		 * 
		 * <p>
		 * Objects of the following type(s) are allowed in the list {@link NewBusinessDTO.ImpGrpCvg.Row }
		 * 
		 * 
		 */
		public List<NewBusinessDTO.ImpGrpCvg.Row> getRows() {
			if (rows == null) {
				rows = new ArrayList<NewBusinessDTO.ImpGrpCvg.Row>();
			}
			return this.rows;
		}


		/**
		 * <p>
		 * Java class for anonymous complex type.
		 * 
		 * <p>
		 * The following schema fragment specifies the expected content contained within this class.
		 * 
		 * <pre>
		 * &lt;complexType&gt;
		 *   &lt;complexContent&gt;
		 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
		 *       &lt;sequence&gt;
		 *         &lt;element name="ImpItmPclAssured2PolicyCvgLives" minOccurs="0"&gt;
		 *           &lt;complexType&gt;
		 *             &lt;complexContent&gt;
		 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
		 *                 &lt;sequence&gt;
		 *                   &lt;element name="Client" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
		 *                   &lt;element name="AgeRating" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
		 *                   &lt;element name="AddnFactor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                   &lt;element name="AddnRpm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                 &lt;/sequence&gt;
		 *               &lt;/restriction&gt;
		 *             &lt;/complexContent&gt;
		 *           &lt;/complexType&gt;
		 *         &lt;/element&gt;
		 *         &lt;element name="ImpItmPclAssured1PolicyCvgLives" minOccurs="0"&gt;
		 *           &lt;complexType&gt;
		 *             &lt;complexContent&gt;
		 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
		 *                 &lt;sequence&gt;
		 *                   &lt;element name="Client" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
		 *                   &lt;element name="AgeRating" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
		 *                   &lt;element name="AddnFactor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                   &lt;element name="AddnRpm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                 &lt;/sequence&gt;
		 *               &lt;/restriction&gt;
		 *             &lt;/complexContent&gt;
		 *           &lt;/complexType&gt;
		 *         &lt;/element&gt;
		 *         &lt;element name="ImpItmPcpPolicyPremiums" minOccurs="0"&gt;
		 *           &lt;complexType&gt;
		 *             &lt;complexContent&gt;
		 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
		 *                 &lt;sequence&gt;
		 *                   &lt;element name="ModalPremium" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                   &lt;element name="Currency" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                   &lt;element name="Frequency" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
		 *                 &lt;/sequence&gt;
		 *               &lt;/restriction&gt;
		 *             &lt;/complexContent&gt;
		 *           &lt;/complexType&gt;
		 *         &lt;/element&gt;
		 *         &lt;element name="ImpItmPocPolicyCoverages" minOccurs="0"&gt;
		 *           &lt;complexType&gt;
		 *             &lt;complexContent&gt;
		 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
		 *                 &lt;sequence&gt;
		 *                   &lt;element name="ProductLine" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                   &lt;element name="Term" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                   &lt;element name="Currency" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                   &lt;element name="InitialSumAssured" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                   &lt;element name="Coverage" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
		 *                   &lt;element name="SoldBasis" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
		 *                   &lt;element name="Multiplier" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                   &lt;element name="LivesType" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
		 *                   &lt;element name="DateOfApplication" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                   &lt;element name="DateOfReqCommencement" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                   &lt;element name="DateCommenced" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                 &lt;/sequence&gt;
		 *               &lt;/restriction&gt;
		 *             &lt;/complexContent&gt;
		 *           &lt;/complexType&gt;
		 *         &lt;/element&gt;
		 *       &lt;/sequence&gt;
		 *     &lt;/restriction&gt;
		 *   &lt;/complexContent&gt;
		 * &lt;/complexType&gt;
		 * </pre>
		 * 
		 * 
		 */
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = {
				"impItmPclAssured2PolicyCvgLives",
				"impItmPclAssured1PolicyCvgLives",
				"impItmPcpPolicyPremiums",
				"impItmPocPolicyCoverages"
		})
		public static class Row {

			@XmlElement(name = "ImpItmPclAssured2PolicyCvgLives")
			protected NewBusinessDTO.ImpGrpCvg.Row.ImpItmPclAssured2PolicyCvgLives impItmPclAssured2PolicyCvgLives;
			@XmlElement(name = "ImpItmPclAssured1PolicyCvgLives")
			protected NewBusinessDTO.ImpGrpCvg.Row.ImpItmPclAssured1PolicyCvgLives impItmPclAssured1PolicyCvgLives;
			@XmlElement(name = "ImpItmPcpPolicyPremiums")
			protected NewBusinessDTO.ImpGrpCvg.Row.ImpItmPcpPolicyPremiums impItmPcpPolicyPremiums;
			@XmlElement(name = "ImpItmPocPolicyCoverages")
			protected NewBusinessDTO.ImpGrpCvg.Row.ImpItmPocPolicyCoverages impItmPocPolicyCoverages;

			/**
			 * Gets the value of the impItmPclAssured2PolicyCvgLives property.
			 * 
			 * @return possible object is {@link NewBusinessDTO.ImpGrpCvg.Row.ImpItmPclAssured2PolicyCvgLives }
			 * 
			 */
			public NewBusinessDTO.ImpGrpCvg.Row.ImpItmPclAssured2PolicyCvgLives getImpItmPclAssured2PolicyCvgLives() {
				return impItmPclAssured2PolicyCvgLives;
			}

			/**
			 * Sets the value of the impItmPclAssured2PolicyCvgLives property.
			 * 
			 * @param value allowed object is {@link NewBusinessDTO.ImpGrpCvg.Row.ImpItmPclAssured2PolicyCvgLives }
			 * 
			 */
			public void setImpItmPclAssured2PolicyCvgLives(NewBusinessDTO.ImpGrpCvg.Row.ImpItmPclAssured2PolicyCvgLives value) {
				this.impItmPclAssured2PolicyCvgLives = value;
			}

			/**
			 * Gets the value of the impItmPclAssured1PolicyCvgLives property.
			 * 
			 * @return possible object is {@link NewBusinessDTO.ImpGrpCvg.Row.ImpItmPclAssured1PolicyCvgLives }
			 * 
			 */
			public NewBusinessDTO.ImpGrpCvg.Row.ImpItmPclAssured1PolicyCvgLives getImpItmPclAssured1PolicyCvgLives() {
				return impItmPclAssured1PolicyCvgLives;
			}

			/**
			 * Sets the value of the impItmPclAssured1PolicyCvgLives property.
			 * 
			 * @param value allowed object is {@link NewBusinessDTO.ImpGrpCvg.Row.ImpItmPclAssured1PolicyCvgLives }
			 * 
			 */
			public void setImpItmPclAssured1PolicyCvgLives(NewBusinessDTO.ImpGrpCvg.Row.ImpItmPclAssured1PolicyCvgLives value) {
				this.impItmPclAssured1PolicyCvgLives = value;
			}

			/**
			 * Gets the value of the impItmPcpPolicyPremiums property.
			 * 
			 * @return possible object is {@link NewBusinessDTO.ImpGrpCvg.Row.ImpItmPcpPolicyPremiums }
			 * 
			 */
			public NewBusinessDTO.ImpGrpCvg.Row.ImpItmPcpPolicyPremiums getImpItmPcpPolicyPremiums() {
				return impItmPcpPolicyPremiums;
			}

			/**
			 * Sets the value of the impItmPcpPolicyPremiums property.
			 * 
			 * @param value allowed object is {@link NewBusinessDTO.ImpGrpCvg.Row.ImpItmPcpPolicyPremiums }
			 * 
			 */
			public void setImpItmPcpPolicyPremiums(NewBusinessDTO.ImpGrpCvg.Row.ImpItmPcpPolicyPremiums value) {
				this.impItmPcpPolicyPremiums = value;
			}

			/**
			 * Gets the value of the impItmPocPolicyCoverages property.
			 * 
			 * @return possible object is {@link NewBusinessDTO.ImpGrpCvg.Row.ImpItmPocPolicyCoverages }
			 * 
			 */
			public NewBusinessDTO.ImpGrpCvg.Row.ImpItmPocPolicyCoverages getImpItmPocPolicyCoverages() {
				return impItmPocPolicyCoverages;
			}

			/**
			 * Sets the value of the impItmPocPolicyCoverages property.
			 * 
			 * @param value allowed object is {@link NewBusinessDTO.ImpGrpCvg.Row.ImpItmPocPolicyCoverages }
			 * 
			 */
			public void setImpItmPocPolicyCoverages(NewBusinessDTO.ImpGrpCvg.Row.ImpItmPocPolicyCoverages value) {
				this.impItmPocPolicyCoverages = value;
			}


			/**
			 * <p>
			 * Java class for anonymous complex type.
			 * 
			 * <p>
			 * The following schema fragment specifies the expected content contained within this class.
			 * 
			 * <pre>
			 * &lt;complexType&gt;
			 *   &lt;complexContent&gt;
			 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
			 *       &lt;sequence&gt;
			 *         &lt;element name="Client" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
			 *         &lt;element name="AgeRating" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
			 *         &lt;element name="AddnFactor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *         &lt;element name="AddnRpm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *       &lt;/sequence&gt;
			 *     &lt;/restriction&gt;
			 *   &lt;/complexContent&gt;
			 * &lt;/complexType&gt;
			 * </pre>
			 * 
			 * 
			 */
			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = {
					"client",
					"ageRating",
					"addnFactor",
					"addnRpm"
			})
			public static class ImpItmPclAssured1PolicyCvgLives {

				@XmlElement(name = "Client")
				protected long client;
				@XmlElement(name = "AgeRating")
				protected short ageRating;
				@XmlElement(name = "AddnFactor", required = true)
				protected String addnFactor;
				@XmlElement(name = "AddnRpm", required = true)
				protected String addnRpm;

				/**
				 * Gets the value of the client property.
				 * 
				 */
				public long getClient() {
					return client;
				}

				/**
				 * Sets the value of the client property.
				 * 
				 */
				public void setClient(long value) {
					this.client = value;
				}

				/**
				 * Gets the value of the ageRating property.
				 * 
				 */
				public short getAgeRating() {
					return ageRating;
				}

				/**
				 * Sets the value of the ageRating property.
				 * 
				 */
				public void setAgeRating(short value) {
					this.ageRating = value;
				}

				/**
				 * Gets the value of the addnFactor property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getAddnFactor() {
					return addnFactor;
				}

				/**
				 * Sets the value of the addnFactor property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setAddnFactor(String value) {
					this.addnFactor = value;
				}

				/**
				 * Gets the value of the addnRpm property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getAddnRpm() {
					return addnRpm;
				}

				/**
				 * Sets the value of the addnRpm property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setAddnRpm(String value) {
					this.addnRpm = value;
				}

			}


			/**
			 * <p>
			 * Java class for anonymous complex type.
			 * 
			 * <p>
			 * The following schema fragment specifies the expected content contained within this class.
			 * 
			 * <pre>
			 * &lt;complexType&gt;
			 *   &lt;complexContent&gt;
			 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
			 *       &lt;sequence&gt;
			 *         &lt;element name="Client" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
			 *         &lt;element name="AgeRating" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
			 *         &lt;element name="AddnFactor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *         &lt;element name="AddnRpm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *       &lt;/sequence&gt;
			 *     &lt;/restriction&gt;
			 *   &lt;/complexContent&gt;
			 * &lt;/complexType&gt;
			 * </pre>
			 * 
			 * 
			 */
			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = {
					"client",
					"ageRating",
					"addnFactor",
					"addnRpm"
			})
			public static class ImpItmPclAssured2PolicyCvgLives {

				@XmlElement(name = "Client")
				protected long client;
				@XmlElement(name = "AgeRating")
				protected short ageRating;
				@XmlElement(name = "AddnFactor", required = true)
				protected String addnFactor;
				@XmlElement(name = "AddnRpm", required = true)
				protected String addnRpm;

				/**
				 * Gets the value of the client property.
				 * 
				 */
				public long getClient() {
					return client;
				}

				/**
				 * Sets the value of the client property.
				 * 
				 */
				public void setClient(long value) {
					this.client = value;
				}

				/**
				 * Gets the value of the ageRating property.
				 * 
				 */
				public short getAgeRating() {
					return ageRating;
				}

				/**
				 * Sets the value of the ageRating property.
				 * 
				 */
				public void setAgeRating(short value) {
					this.ageRating = value;
				}

				/**
				 * Gets the value of the addnFactor property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getAddnFactor() {
					return addnFactor;
				}

				/**
				 * Sets the value of the addnFactor property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setAddnFactor(String value) {
					this.addnFactor = value;
				}

				/**
				 * Gets the value of the addnRpm property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getAddnRpm() {
					return addnRpm;
				}

				/**
				 * Sets the value of the addnRpm property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setAddnRpm(String value) {
					this.addnRpm = value;
				}

			}


			/**
			 * <p>
			 * Java class for anonymous complex type.
			 * 
			 * <p>
			 * The following schema fragment specifies the expected content contained within this class.
			 * 
			 * <pre>
			 * &lt;complexType&gt;
			 *   &lt;complexContent&gt;
			 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
			 *       &lt;sequence&gt;
			 *         &lt;element name="ModalPremium" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *         &lt;element name="Currency" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *         &lt;element name="Frequency" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
			 *       &lt;/sequence&gt;
			 *     &lt;/restriction&gt;
			 *   &lt;/complexContent&gt;
			 * &lt;/complexType&gt;
			 * </pre>
			 * 
			 * 
			 */
			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = {
					"modalPremium",
					"currency",
					"frequency"
			})
			public static class ImpItmPcpPolicyPremiums {

				@XmlElement(name = "ModalPremium", required = true)
				protected String modalPremium;
				@XmlElement(name = "Currency", required = true)
				protected String currency;
				@XmlElement(name = "Frequency")
				protected short frequency;

				/**
				 * Gets the value of the modalPremium property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getModalPremium() {
					return modalPremium;
				}

				/**
				 * Sets the value of the modalPremium property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setModalPremium(String value) {
					this.modalPremium = value;
				}

				/**
				 * Gets the value of the currency property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getCurrency() {
					return currency;
				}

				/**
				 * Sets the value of the currency property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setCurrency(String value) {
					this.currency = value;
				}

				/**
				 * Gets the value of the frequency property.
				 * 
				 */
				public short getFrequency() {
					return frequency;
				}

				/**
				 * Sets the value of the frequency property.
				 * 
				 */
				public void setFrequency(short value) {
					this.frequency = value;
				}

			}


			/**
			 * <p>
			 * Java class for anonymous complex type.
			 * 
			 * <p>
			 * The following schema fragment specifies the expected content contained within this class.
			 * 
			 * <pre>
			 * &lt;complexType&gt;
			 *   &lt;complexContent&gt;
			 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
			 *       &lt;sequence&gt;
			 *         &lt;element name="ProductLine" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *         &lt;element name="Term" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *         &lt;element name="Currency" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *         &lt;element name="InitialSumAssured" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *         &lt;element name="Coverage" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
			 *         &lt;element name="SoldBasis" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
			 *         &lt;element name="Multiplier" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *         &lt;element name="LivesType" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
			 *         &lt;element name="DateOfApplication" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *         &lt;element name="DateOfReqCommencement" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *         &lt;element name="DateCommenced" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *       &lt;/sequence&gt;
			 *     &lt;/restriction&gt;
			 *   &lt;/complexContent&gt;
			 * &lt;/complexType&gt;
			 * </pre>
			 * 
			 * 
			 */
			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = {
					"productLine",
					"term",
					"currency",
					"initialSumAssured",
					"coverage",
					"soldBasis",
					"multiplier",
					"livesType",
					"dateOfApplication",
					"dateOfReqCommencement",
					"dateCommenced"
			})
			public static class ImpItmPocPolicyCoverages {

				@XmlElement(name = "ProductLine", required = true)
				protected String productLine;
				@XmlElement(name = "Term", required = true)
				protected String term;
				@XmlElement(name = "Currency", required = true)
				protected String currency;
				@XmlElement(name = "InitialSumAssured", required = true)
				protected String initialSumAssured;
				@XmlElement(name = "Coverage")
				protected short coverage;
				@XmlElement(name = "SoldBasis")
				protected short soldBasis;
				@XmlElement(name = "Multiplier", required = true)
				protected String multiplier;
				@XmlElement(name = "LivesType")
				protected short livesType;
				@XmlElement(name = "DateOfApplication", required = true)
				protected String dateOfApplication;
				@XmlElement(name = "DateOfReqCommencement", required = true)
				protected String dateOfReqCommencement;
				@XmlElement(name = "DateCommenced", required = true)
				protected String dateCommenced;

				/**
				 * Gets the value of the productLine property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getProductLine() {
					return productLine;
				}

				/**
				 * Sets the value of the productLine property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setProductLine(String value) {
					this.productLine = value;
				}

				/**
				 * Gets the value of the term property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getTerm() {
					return term;
				}

				/**
				 * Sets the value of the term property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setTerm(String value) {
					this.term = value;
				}

				/**
				 * Gets the value of the currency property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getCurrency() {
					return currency;
				}

				/**
				 * Sets the value of the currency property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setCurrency(String value) {
					this.currency = value;
				}

				/**
				 * Gets the value of the initialSumAssured property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getInitialSumAssured() {
					return initialSumAssured;
				}

				/**
				 * Sets the value of the initialSumAssured property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setInitialSumAssured(String value) {
					this.initialSumAssured = value;
				}

				/**
				 * Gets the value of the coverage property.
				 * 
				 */
				public short getCoverage() {
					return coverage;
				}

				/**
				 * Sets the value of the coverage property.
				 * 
				 */
				public void setCoverage(short value) {
					this.coverage = value;
				}

				/**
				 * Gets the value of the soldBasis property.
				 * 
				 */
				public short getSoldBasis() {
					return soldBasis;
				}

				/**
				 * Sets the value of the soldBasis property.
				 * 
				 */
				public void setSoldBasis(short value) {
					this.soldBasis = value;
				}

				/**
				 * Gets the value of the multiplier property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getMultiplier() {
					return multiplier;
				}

				/**
				 * Sets the value of the multiplier property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setMultiplier(String value) {
					this.multiplier = value;
				}

				/**
				 * Gets the value of the livesType property.
				 * 
				 */
				public short getLivesType() {
					return livesType;
				}

				/**
				 * Sets the value of the livesType property.
				 * 
				 */
				public void setLivesType(short value) {
					this.livesType = value;
				}

				/**
				 * Gets the value of the dateOfApplication property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getDateOfApplication() {
					return dateOfApplication;
				}

				/**
				 * Sets the value of the dateOfApplication property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setDateOfApplication(String value) {
					this.dateOfApplication = value;
				}

				/**
				 * Gets the value of the dateOfReqCommencement property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getDateOfReqCommencement() {
					return dateOfReqCommencement;
				}

				/**
				 * Sets the value of the dateOfReqCommencement property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setDateOfReqCommencement(String value) {
					this.dateOfReqCommencement = value;
				}

				/**
				 * Gets the value of the dateCommenced property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getDateCommenced() {
					return dateCommenced;
				}

				/**
				 * Sets the value of the dateCommenced property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setDateCommenced(String value) {
					this.dateCommenced = value;
				}

			}

		}

	}


	/**
	 * <p>
	 * Java class for anonymous complex type.
	 * 
	 * <p>
	 * The following schema fragment specifies the expected content contained within this class.
	 * 
	 * <pre>
	 * &lt;complexType&gt;
	 *   &lt;complexContent&gt;
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *       &lt;sequence&gt;
	 *         &lt;element name="row" maxOccurs="unbounded" minOccurs="0"&gt;
	 *           &lt;complexType&gt;
	 *             &lt;complexContent&gt;
	 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *                 &lt;sequence&gt;
	 *                   &lt;element name="ImpItmPrlProductLines" minOccurs="0"&gt;
	 *                     &lt;complexType&gt;
	 *                       &lt;complexContent&gt;
	 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *                           &lt;sequence&gt;
	 *                             &lt;element name="PrlId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                           &lt;/sequence&gt;
	 *                         &lt;/restriction&gt;
	 *                       &lt;/complexContent&gt;
	 *                     &lt;/complexType&gt;
	 *                   &lt;/element&gt;
	 *                   &lt;element name="ImpItmPasPolicyAgentShares" minOccurs="0"&gt;
	 *                     &lt;complexType&gt;
	 *                       &lt;complexContent&gt;
	 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *                           &lt;sequence&gt;
	 *                             &lt;element name="Agent" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                             &lt;element name="Percentage" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                             &lt;element name="PartnerAuthorized" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
	 *                             &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
	 *                             &lt;element name="SpecificIce" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                             &lt;element name="PrimaryAgent" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
	 *                           &lt;/sequence&gt;
	 *                         &lt;/restriction&gt;
	 *                       &lt;/complexContent&gt;
	 *                     &lt;/complexType&gt;
	 *                   &lt;/element&gt;
	 *                 &lt;/sequence&gt;
	 *               &lt;/restriction&gt;
	 *             &lt;/complexContent&gt;
	 *           &lt;/complexType&gt;
	 *         &lt;/element&gt;
	 *       &lt;/sequence&gt;
	 *     &lt;/restriction&gt;
	 *   &lt;/complexContent&gt;
	 * &lt;/complexType&gt;
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
			"rows"
	})
	public static class ImpGrpPas {

		@XmlElement(name = "row")
		protected List<NewBusinessDTO.ImpGrpPas.Row> rows;

		/**
		 * Gets the value of the rows property.
		 * 
		 * <p>
		 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there
		 * is not a <CODE>set</CODE> method for the rows property.
		 * 
		 * <p>
		 * For example, to add a new item, do as follows:
		 * 
		 * <pre>
		 * getRows().add(newItem);
		 * </pre>
		 * 
		 * 
		 * <p>
		 * Objects of the following type(s) are allowed in the list {@link NewBusinessDTO.ImpGrpPas.Row }
		 * 
		 * 
		 */
		public List<NewBusinessDTO.ImpGrpPas.Row> getRows() {
			if (rows == null) {
				rows = new ArrayList<NewBusinessDTO.ImpGrpPas.Row>();
			}
			return this.rows;
		}


		/**
		 * <p>
		 * Java class for anonymous complex type.
		 * 
		 * <p>
		 * The following schema fragment specifies the expected content contained within this class.
		 * 
		 * <pre>
		 * &lt;complexType&gt;
		 *   &lt;complexContent&gt;
		 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
		 *       &lt;sequence&gt;
		 *         &lt;element name="ImpItmPrlProductLines" minOccurs="0"&gt;
		 *           &lt;complexType&gt;
		 *             &lt;complexContent&gt;
		 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
		 *                 &lt;sequence&gt;
		 *                   &lt;element name="PrlId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                 &lt;/sequence&gt;
		 *               &lt;/restriction&gt;
		 *             &lt;/complexContent&gt;
		 *           &lt;/complexType&gt;
		 *         &lt;/element&gt;
		 *         &lt;element name="ImpItmPasPolicyAgentShares" minOccurs="0"&gt;
		 *           &lt;complexType&gt;
		 *             &lt;complexContent&gt;
		 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
		 *                 &lt;sequence&gt;
		 *                   &lt;element name="Agent" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                   &lt;element name="Percentage" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                   &lt;element name="PartnerAuthorized" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
		 *                   &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
		 *                   &lt;element name="SpecificIce" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                   &lt;element name="PrimaryAgent" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
		 *                 &lt;/sequence&gt;
		 *               &lt;/restriction&gt;
		 *             &lt;/complexContent&gt;
		 *           &lt;/complexType&gt;
		 *         &lt;/element&gt;
		 *       &lt;/sequence&gt;
		 *     &lt;/restriction&gt;
		 *   &lt;/complexContent&gt;
		 * &lt;/complexType&gt;
		 * </pre>
		 * 
		 * 
		 */
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = {
				"impItmPrlProductLines",
				"impItmPasPolicyAgentShares"
		})
		public static class Row {

			@XmlElement(name = "ImpItmPrlProductLines")
			protected NewBusinessDTO.ImpGrpPas.Row.ImpItmPrlProductLines impItmPrlProductLines;
			@XmlElement(name = "ImpItmPasPolicyAgentShares")
			protected NewBusinessDTO.ImpGrpPas.Row.ImpItmPasPolicyAgentShares impItmPasPolicyAgentShares;

			/**
			 * Gets the value of the impItmPrlProductLines property.
			 * 
			 * @return possible object is {@link NewBusinessDTO.ImpGrpPas.Row.ImpItmPrlProductLines }
			 * 
			 */
			public NewBusinessDTO.ImpGrpPas.Row.ImpItmPrlProductLines getImpItmPrlProductLines() {
				return impItmPrlProductLines;
			}

			/**
			 * Sets the value of the impItmPrlProductLines property.
			 * 
			 * @param value allowed object is {@link NewBusinessDTO.ImpGrpPas.Row.ImpItmPrlProductLines }
			 * 
			 */
			public void setImpItmPrlProductLines(NewBusinessDTO.ImpGrpPas.Row.ImpItmPrlProductLines value) {
				this.impItmPrlProductLines = value;
			}

			/**
			 * Gets the value of the impItmPasPolicyAgentShares property.
			 * 
			 * @return possible object is {@link NewBusinessDTO.ImpGrpPas.Row.ImpItmPasPolicyAgentShares }
			 * 
			 */
			public NewBusinessDTO.ImpGrpPas.Row.ImpItmPasPolicyAgentShares getImpItmPasPolicyAgentShares() {
				return impItmPasPolicyAgentShares;
			}

			/**
			 * Sets the value of the impItmPasPolicyAgentShares property.
			 * 
			 * @param value allowed object is {@link NewBusinessDTO.ImpGrpPas.Row.ImpItmPasPolicyAgentShares }
			 * 
			 */
			public void setImpItmPasPolicyAgentShares(NewBusinessDTO.ImpGrpPas.Row.ImpItmPasPolicyAgentShares value) {
				this.impItmPasPolicyAgentShares = value;
			}

			/**
			 * <p>
			 * Java class for anonymous complex type.
			 * 
			 * <p>
			 * The following schema fragment specifies the expected content contained within this class.
			 * 
			 * <pre>
			 * &lt;complexType&gt;
			 *   &lt;complexContent&gt;
			 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
			 *       &lt;sequence&gt;
			 *         &lt;element name="Agent" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *         &lt;element name="Percentage" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *         &lt;element name="PartnerAuthorized" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
			 *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
			 *         &lt;element name="SpecificIce" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *         &lt;element name="PrimaryAgent" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
			 *       &lt;/sequence&gt;
			 *     &lt;/restriction&gt;
			 *   &lt;/complexContent&gt;
			 * &lt;/complexType&gt;
			 * </pre>
			 * 
			 * 
			 */
			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = {
					"agent",
					"percentage",
					"partnerAuthorized",
					"type",
					"specificIce",
					"primaryAgent"
			})
			public static class ImpItmPasPolicyAgentShares {

				@XmlElement(name = "Agent", required = true)
				protected String agent;
				@XmlElement(name = "Percentage", required = true)
				protected String percentage;
				@XmlElement(name = "PartnerAuthorized")
				protected short partnerAuthorized;
				@XmlElement(name = "Type")
				protected short type;
				@XmlElement(name = "SpecificIce", required = true)
				protected String specificIce;
				@XmlElement(name = "PrimaryAgent")
				protected short primaryAgent;

				/**
				 * Gets the value of the agent property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getAgent() {
					return agent;
				}

				/**
				 * Sets the value of the agent property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setAgent(String value) {
					this.agent = value;
				}

				/**
				 * Gets the value of the percentage property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getPercentage() {
					return percentage;
				}

				/**
				 * Sets the value of the percentage property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setPercentage(String value) {
					this.percentage = value;
				}

				/**
				 * Gets the value of the partnerAuthorized property.
				 * 
				 */
				public short getPartnerAuthorized() {
					return partnerAuthorized;
				}

				/**
				 * Sets the value of the partnerAuthorized property.
				 * 
				 */
				public void setPartnerAuthorized(short value) {
					this.partnerAuthorized = value;
				}

				/**
				 * Gets the value of the type property.
				 * 
				 */
				public short getType() {
					return type;
				}

				/**
				 * Sets the value of the type property.
				 * 
				 */
				public void setType(short value) {
					this.type = value;
				}

				/**
				 * Gets the value of the specificIce property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getSpecificIce() {
					return specificIce;
				}

				/**
				 * Sets the value of the specificIce property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setSpecificIce(String value) {
					this.specificIce = value;
				}

				/**
				 * Gets the value of the primaryAgent property.
				 * 
				 */
				public short getPrimaryAgent() {
					return primaryAgent;
				}

				/**
				 * Sets the value of the primaryAgent property.
				 * 
				 */
				public void setPrimaryAgent(short value) {
					this.primaryAgent = value;
				}

			}


			/**
			 * <p>
			 * Java class for anonymous complex type.
			 * 
			 * <p>
			 * The following schema fragment specifies the expected content contained within this class.
			 * 
			 * <pre>
			 * &lt;complexType&gt;
			 *   &lt;complexContent&gt;
			 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
			 *       &lt;sequence&gt;
			 *         &lt;element name="PrlId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *       &lt;/sequence&gt;
			 *     &lt;/restriction&gt;
			 *   &lt;/complexContent&gt;
			 * &lt;/complexType&gt;
			 * </pre>
			 * 
			 * 
			 */
			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = {
					"prlId"
			})
			public static class ImpItmPrlProductLines {

				@XmlElement(name = "PrlId", required = true)
				protected String prlId;

				/**
				 * Gets the value of the prlId property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getPrlId() {
					return prlId;
				}

				/**
				 * Sets the value of the prlId property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setPrlId(String value) {
					this.prlId = value;
				}

			}

		}

	}


	/**
	 * <p>
	 * Java class for anonymous complex type.
	 * 
	 * <p>
	 * The following schema fragment specifies the expected content contained within this class.
	 * 
	 * <pre>
	 * &lt;complexType&gt;
	 *   &lt;complexContent&gt;
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *       &lt;sequence&gt;
	 *         &lt;element name="row" maxOccurs="unbounded" minOccurs="0"&gt;
	 *           &lt;complexType&gt;
	 *             &lt;complexContent&gt;
	 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *                 &lt;sequence&gt;
	 *                   &lt;element name="ImpItmPbcPolicyBeneficiaryClauses" minOccurs="0"&gt;
	 *                     &lt;complexType&gt;
	 *                       &lt;complexContent&gt;
	 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *                           &lt;sequence&gt;
	 *                             &lt;element name="Policy" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                             &lt;element name="Rank" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
	 *                             &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                             &lt;element name="TypeOfClause" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                             &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
	 *                             &lt;element name="TextOfClause" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                             &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                           &lt;/sequence&gt;
	 *                         &lt;/restriction&gt;
	 *                       &lt;/complexContent&gt;
	 *                     &lt;/complexType&gt;
	 *                   &lt;/element&gt;
	 *                 &lt;/sequence&gt;
	 *               &lt;/restriction&gt;
	 *             &lt;/complexContent&gt;
	 *           &lt;/complexType&gt;
	 *         &lt;/element&gt;
	 *       &lt;/sequence&gt;
	 *     &lt;/restriction&gt;
	 *   &lt;/complexContent&gt;
	 * &lt;/complexType&gt;
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
			"rows"
	})
	public static class ImpGrpPbc {

		@XmlElement(name = "row")
		protected List<NewBusinessDTO.ImpGrpPbc.Row> rows;

		/**
		 * Gets the value of the rows property.
		 * 
		 * <p>
		 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there
		 * is not a <CODE>set</CODE> method for the rows property.
		 * 
		 * <p>
		 * For example, to add a new item, do as follows:
		 * 
		 * <pre>
		 * getRows().add(newItem);
		 * </pre>
		 * 
		 * 
		 * <p>
		 * Objects of the following type(s) are allowed in the list {@link NewBusinessDTO.ImpGrpPbc.Row }
		 * 
		 * 
		 */
		public List<NewBusinessDTO.ImpGrpPbc.Row> getRows() {
			if (rows == null) {
				rows = new ArrayList<NewBusinessDTO.ImpGrpPbc.Row>();
			}
			return this.rows;
		}


		/**
		 * <p>
		 * Java class for anonymous complex type.
		 * 
		 * <p>
		 * The following schema fragment specifies the expected content contained within this class.
		 * 
		 * <pre>
		 * &lt;complexType&gt;
		 *   &lt;complexContent&gt;
		 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
		 *       &lt;sequence&gt;
		 *         &lt;element name="ImpItmPbcPolicyBeneficiaryClauses" minOccurs="0"&gt;
		 *           &lt;complexType&gt;
		 *             &lt;complexContent&gt;
		 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
		 *                 &lt;sequence&gt;
		 *                   &lt;element name="Policy" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                   &lt;element name="Rank" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
		 *                   &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                   &lt;element name="TypeOfClause" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                   &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
		 *                   &lt;element name="TextOfClause" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                   &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                 &lt;/sequence&gt;
		 *               &lt;/restriction&gt;
		 *             &lt;/complexContent&gt;
		 *           &lt;/complexType&gt;
		 *         &lt;/element&gt;
		 *       &lt;/sequence&gt;
		 *     &lt;/restriction&gt;
		 *   &lt;/complexContent&gt;
		 * &lt;/complexType&gt;
		 * </pre>
		 * 
		 * 
		 */
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = {
				"impItmPbcPolicyBeneficiaryClauses"
		})
		public static class Row {

			@XmlElement(name = "ImpItmPbcPolicyBeneficiaryClauses")
			protected NewBusinessDTO.ImpGrpPbc.Row.ImpItmPbcPolicyBeneficiaryClauses impItmPbcPolicyBeneficiaryClauses;

			/**
			 * Gets the value of the impItmPbcPolicyBeneficiaryClauses property.
			 * 
			 * @return possible object is {@link NewBusinessDTO.ImpGrpPbc.Row.ImpItmPbcPolicyBeneficiaryClauses }
			 * 
			 */
			public NewBusinessDTO.ImpGrpPbc.Row.ImpItmPbcPolicyBeneficiaryClauses getImpItmPbcPolicyBeneficiaryClauses() {
				return impItmPbcPolicyBeneficiaryClauses;
			}

			/**
			 * Sets the value of the impItmPbcPolicyBeneficiaryClauses property.
			 * 
			 * @param value allowed object is {@link NewBusinessDTO.ImpGrpPbc.Row.ImpItmPbcPolicyBeneficiaryClauses }
			 * 
			 */
			public void setImpItmPbcPolicyBeneficiaryClauses(NewBusinessDTO.ImpGrpPbc.Row.ImpItmPbcPolicyBeneficiaryClauses value) {
				this.impItmPbcPolicyBeneficiaryClauses = value;
			}


			/**
			 * <p>
			 * Java class for anonymous complex type.
			 * 
			 * <p>
			 * The following schema fragment specifies the expected content contained within this class.
			 * 
			 * <pre>
			 * &lt;complexType&gt;
			 *   &lt;complexContent&gt;
			 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
			 *       &lt;sequence&gt;
			 *         &lt;element name="Policy" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *         &lt;element name="Rank" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
			 *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *         &lt;element name="TypeOfClause" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
			 *         &lt;element name="TextOfClause" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *         &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *       &lt;/sequence&gt;
			 *     &lt;/restriction&gt;
			 *   &lt;/complexContent&gt;
			 * &lt;/complexType&gt;
			 * </pre>
			 * 
			 * 
			 */
			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = {
					"policy",
					"rank",
					"type",
					"typeOfClause",
					"status",
					"textOfClause",
					"code"
			})
			public static class ImpItmPbcPolicyBeneficiaryClauses {

				@XmlElement(name = "Policy", required = true)
				protected String policy;
				@XmlElement(name = "Rank")
				protected short rank;
				@XmlElement(name = "Type", required = true)
				protected String type;
				@XmlElement(name = "TypeOfClause", required = true)
				protected String typeOfClause;
				@XmlElement(name = "Status")
				protected short status;
				@XmlElement(name = "TextOfClause", required = true)
				protected String textOfClause;
				@XmlElement(name = "Code", required = true)
				protected String code;

				/**
				 * Gets the value of the policy property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getPolicy() {
					return policy;
				}

				/**
				 * Sets the value of the policy property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setPolicy(String value) {
					this.policy = value;
				}

				/**
				 * Gets the value of the rank property.
				 * 
				 */
				public short getRank() {
					return rank;
				}

				/**
				 * Sets the value of the rank property.
				 * 
				 */
				public void setRank(short value) {
					this.rank = value;
				}

				/**
				 * Gets the value of the type property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getType() {
					return type;
				}

				/**
				 * Sets the value of the type property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setType(String value) {
					this.type = value;
				}

				/**
				 * Gets the value of the typeOfClause property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getTypeOfClause() {
					return typeOfClause;
				}

				/**
				 * Sets the value of the typeOfClause property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setTypeOfClause(String value) {
					this.typeOfClause = value;
				}

				/**
				 * Gets the value of the status property.
				 * 
				 */
				public short getStatus() {
					return status;
				}

				/**
				 * Sets the value of the status property.
				 * 
				 */
				public void setStatus(short value) {
					this.status = value;
				}

				/**
				 * Gets the value of the textOfClause property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getTextOfClause() {
					return textOfClause;
				}

				/**
				 * Sets the value of the textOfClause property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setTextOfClause(String value) {
					this.textOfClause = value;
				}

				/**
				 * Gets the value of the code property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getCode() {
					return code;
				}

				/**
				 * Sets the value of the code property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setCode(String value) {
					this.code = value;
				}

			}

		}

	}


	/**
	 * <p>
	 * Java class for anonymous complex type.
	 * 
	 * <p>
	 * The following schema fragment specifies the expected content contained within this class.
	 * 
	 * <pre>
	 * &lt;complexType&gt;
	 *   &lt;complexContent&gt;
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *       &lt;sequence&gt;
	 *         &lt;element name="row" maxOccurs="unbounded" minOccurs="0"&gt;
	 *           &lt;complexType&gt;
	 *             &lt;complexContent&gt;
	 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *                 &lt;sequence&gt;
	 *                   &lt;element name="ImpItmPfdPolicyFundDirections" minOccurs="0"&gt;
	 *                     &lt;complexType&gt;
	 *                       &lt;complexContent&gt;
	 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *                           &lt;sequence&gt;
	 *                             &lt;element name="Fund" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                             &lt;element name="Percentage" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *                           &lt;/sequence&gt;
	 *                         &lt;/restriction&gt;
	 *                       &lt;/complexContent&gt;
	 *                     &lt;/complexType&gt;
	 *                   &lt;/element&gt;
	 *                 &lt;/sequence&gt;
	 *               &lt;/restriction&gt;
	 *             &lt;/complexContent&gt;
	 *           &lt;/complexType&gt;
	 *         &lt;/element&gt;
	 *       &lt;/sequence&gt;
	 *     &lt;/restriction&gt;
	 *   &lt;/complexContent&gt;
	 * &lt;/complexType&gt;
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
			"rows"
	})
	public static class ImpGrpPfd {

		@XmlElement(name = "row")
		protected List<NewBusinessDTO.ImpGrpPfd.Row> rows;

		/**
		 * Gets the value of the rows property.
		 * 
		 * <p>
		 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there
		 * is not a <CODE>set</CODE> method for the rows property.
		 * 
		 * <p>
		 * For example, to add a new item, do as follows:
		 * 
		 * <pre>
		 * getRows().add(newItem);
		 * </pre>
		 * 
		 * 
		 * <p>
		 * Objects of the following type(s) are allowed in the list {@link NewBusinessDTO.ImpGrpPfd.Row }
		 * 
		 * 
		 */
		public List<NewBusinessDTO.ImpGrpPfd.Row> getRows() {
			if (rows == null) {
				rows = new ArrayList<NewBusinessDTO.ImpGrpPfd.Row>();
			}
			return this.rows;
		}


		/**
		 * <p>
		 * Java class for anonymous complex type.
		 * 
		 * <p>
		 * The following schema fragment specifies the expected content contained within this class.
		 * 
		 * <pre>
		 * &lt;complexType&gt;
		 *   &lt;complexContent&gt;
		 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
		 *       &lt;sequence&gt;
		 *         &lt;element name="ImpItmPfdPolicyFundDirections" minOccurs="0"&gt;
		 *           &lt;complexType&gt;
		 *             &lt;complexContent&gt;
		 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
		 *                 &lt;sequence&gt;
		 *                   &lt;element name="Fund" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                   &lt;element name="Percentage" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
		 *                 &lt;/sequence&gt;
		 *               &lt;/restriction&gt;
		 *             &lt;/complexContent&gt;
		 *           &lt;/complexType&gt;
		 *         &lt;/element&gt;
		 *       &lt;/sequence&gt;
		 *     &lt;/restriction&gt;
		 *   &lt;/complexContent&gt;
		 * &lt;/complexType&gt;
		 * </pre>
		 * 
		 * 
		 */
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = {
				"impItmPfdPolicyFundDirections"
		})
		public static class Row {

			@XmlElement(name = "ImpItmPfdPolicyFundDirections")
			protected NewBusinessDTO.ImpGrpPfd.Row.ImpItmPfdPolicyFundDirections impItmPfdPolicyFundDirections;

			/**
			 * Gets the value of the impItmPfdPolicyFundDirections property.
			 * 
			 * @return possible object is {@link NewBusinessDTO.ImpGrpPfd.Row.ImpItmPfdPolicyFundDirections }
			 * 
			 */
			public NewBusinessDTO.ImpGrpPfd.Row.ImpItmPfdPolicyFundDirections getImpItmPfdPolicyFundDirections() {
				return impItmPfdPolicyFundDirections;
			}

			/**
			 * Sets the value of the impItmPfdPolicyFundDirections property.
			 * 
			 * @param value allowed object is {@link NewBusinessDTO.ImpGrpPfd.Row.ImpItmPfdPolicyFundDirections }
			 * 
			 */
			public void setImpItmPfdPolicyFundDirections(NewBusinessDTO.ImpGrpPfd.Row.ImpItmPfdPolicyFundDirections value) {
				this.impItmPfdPolicyFundDirections = value;
			}


			/**
			 * <p>
			 * Java class for anonymous complex type.
			 * 
			 * <p>
			 * The following schema fragment specifies the expected content contained within this class.
			 * 
			 * <pre>
			 * &lt;complexType&gt;
			 *   &lt;complexContent&gt;
			 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
			 *       &lt;sequence&gt;
			 *         &lt;element name="Fund" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *         &lt;element name="Percentage" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
			 *       &lt;/sequence&gt;
			 *     &lt;/restriction&gt;
			 *   &lt;/complexContent&gt;
			 * &lt;/complexType&gt;
			 * </pre>
			 * 
			 * 
			 */
			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "", propOrder = {
					"fund",
					"percentage"
			})
			public static class ImpItmPfdPolicyFundDirections {

				@XmlElement(name = "Fund", required = true)
				protected String fund;
				@XmlElement(name = "Percentage", required = true)
				protected String percentage;

				/**
				 * Gets the value of the fund property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getFund() {
					return fund;
				}

				/**
				 * Sets the value of the fund property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setFund(String value) {
					this.fund = value;
				}

				/**
				 * Gets the value of the percentage property.
				 * 
				 * @return possible object is {@link String }
				 * 
				 */
				public String getPercentage() {
					return percentage;
				}

				/**
				 * Sets the value of the percentage property.
				 * 
				 * @param value allowed object is {@link String }
				 * 
				 */
				public void setPercentage(String value) {
					this.percentage = value;
				}

			}

		}

	}


	/**
	 * <p>
	 * Java class for anonymous complex type.
	 * 
	 * <p>
	 * The following schema fragment specifies the expected content contained within this class.
	 * 
	 * <pre>
	 * &lt;complexType&gt;
	 *   &lt;complexContent&gt;
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *       &lt;sequence&gt;
	 *         &lt;element name="PolId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *         &lt;element name="Product" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *         &lt;element name="Currency" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *         &lt;element name="Category" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *         &lt;element name="OrderByFax" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
	 *         &lt;element name="NonSurrenderClauseDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *         &lt;element name="ExpectedPremium" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *         &lt;element name="MailToAgent" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *         &lt;element name="ScoreNewBusiness" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
	 *         &lt;element name="Scudo" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
	 *         &lt;element name="MandatoAllIncasso" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
	 *         &lt;element name="BrokerRefContract" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *         &lt;element name="NoCoolOff" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
	 *         &lt;element name="AdditionalId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *         &lt;element name="IssueCountryOfResidence" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *         &lt;element name="DateOfApplication" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *         &lt;element name="DateOfCommencement" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *       &lt;/sequence&gt;
	 *     &lt;/restriction&gt;
	 *   &lt;/complexContent&gt;
	 * &lt;/complexType&gt;
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
			"polId",
			"product",
			"currency",
			"category",
			"orderByFax",
			"nonSurrenderClauseDate",
			"expectedPremium",
			"mailToAgent",
			"scoreNewBusiness",
			"scudo",
			"mandatoAllIncasso",
			"brokerRefContract",
			"noCoolOff",
			"additionalId",
			"issueCountryOfResidence",
			"dateOfApplication",
			"dateOfCommencement",
			"exPaymentTransfer",
			"exMandate"
	})
	public static class ImpPolicyPolicies {

		@XmlElement(name = "PolId", required = true)
		protected String polId;
		@XmlElement(name = "Product", required = true)
		protected String product;
		@XmlElement(name = "Currency", required = true)
		protected String currency;
		@XmlElement(name = "Category", required = true)
		protected String category;
		@XmlElement(name = "OrderByFax")
		protected short orderByFax;
		@XmlElement(name = "NonSurrenderClauseDate", required = true)
		protected String nonSurrenderClauseDate;
		@XmlElement(name = "ExpectedPremium", required = true)
		protected String expectedPremium;
		@XmlElement(name = "MailToAgent", required = true)
		protected String mailToAgent;
		@XmlElement(name = "ScoreNewBusiness")
		protected int scoreNewBusiness;
		@XmlElement(name = "Scudo")
		protected short scudo;
		@XmlElement(name = "MandatoAllIncasso")
		protected short mandatoAllIncasso;
		@XmlElement(name = "BrokerRefContract", required = true)
		protected String brokerRefContract;
		@XmlElement(name = "NoCoolOff")
		protected short noCoolOff;
		@XmlElement(name = "AdditionalId", required = true)
		protected String additionalId;
		@XmlElement(name = "IssueCountryOfResidence", required = true)
		protected String issueCountryOfResidence;
		@XmlElement(name = "DateOfApplication", required = true)
		protected String dateOfApplication;
		@XmlElement(name = "DateOfCommencement", required = true)
		protected String dateOfCommencement;
		@XmlElement(name = "ExPaymentTransfer")
		protected short exPaymentTransfer;
		@XmlElement(name = "ExMandate")
		protected short exMandate;

		/**
		 * Gets the value of the polId property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getPolId() {
			return polId;
		}

		/**
		 * Sets the value of the polId property.
		 * 
		 * @param value allowed object is {@link String }
		 * 
		 */
		public void setPolId(String value) {
			this.polId = value;
		}

		/**
		 * Gets the value of the product property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getProduct() {
			return product;
		}

		/**
		 * Sets the value of the product property.
		 * 
		 * @param value allowed object is {@link String }
		 * 
		 */
		public void setProduct(String value) {
			this.product = value;
		}

		/**
		 * Gets the value of the currency property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getCurrency() {
			return currency;
		}

		/**
		 * Sets the value of the currency property.
		 * 
		 * @param value allowed object is {@link String }
		 * 
		 */
		public void setCurrency(String value) {
			this.currency = value;
		}

		/**
		 * Gets the value of the category property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getCategory() {
			return category;
		}

		/**
		 * Sets the value of the category property.
		 * 
		 * @param value allowed object is {@link String }
		 * 
		 */
		public void setCategory(String value) {
			this.category = value;
		}

		/**
		 * Gets the value of the orderByFax property.
		 * 
		 */
		public short getOrderByFax() {
			return orderByFax;
		}

		/**
		 * Sets the value of the orderByFax property.
		 * 
		 */
		public void setOrderByFax(short value) {
			this.orderByFax = value;
		}

		/**
		 * Gets the value of the nonSurrenderClauseDate property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getNonSurrenderClauseDate() {
			return nonSurrenderClauseDate;
		}

		/**
		 * Sets the value of the nonSurrenderClauseDate property.
		 * 
		 * @param value allowed object is {@link String }
		 * 
		 */
		public void setNonSurrenderClauseDate(String value) {
			this.nonSurrenderClauseDate = value;
		}

		/**
		 * Gets the value of the expectedPremium property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getExpectedPremium() {
			return expectedPremium;
		}

		/**
		 * Sets the value of the expectedPremium property.
		 * 
		 * @param value allowed object is {@link String }
		 * 
		 */
		public void setExpectedPremium(String value) {
			this.expectedPremium = value;
		}

		/**
		 * Gets the value of the mailToAgent property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getMailToAgent() {
			return mailToAgent;
		}

		/**
		 * Sets the value of the mailToAgent property.
		 * 
		 * @param value allowed object is {@link String }
		 * 
		 */
		public void setMailToAgent(String value) {
			this.mailToAgent = value;
		}

		/**
		 * Gets the value of the scoreNewBusiness property.
		 * 
		 */
		public int getScoreNewBusiness() {
			return scoreNewBusiness;
		}

		/**
		 * Sets the value of the scoreNewBusiness property.
		 * 
		 */
		public void setScoreNewBusiness(int value) {
			this.scoreNewBusiness = value;
		}

		/**
		 * Gets the value of the scudo property.
		 * 
		 */
		public short getScudo() {
			return scudo;
		}

		/**
		 * Sets the value of the scudo property.
		 * 
		 */
		public void setScudo(short value) {
			this.scudo = value;
		}

		/**
		 * Gets the value of the mandatoAllIncasso property.
		 * 
		 */
		public short getMandatoAllIncasso() {
			return mandatoAllIncasso;
		}

		/**
		 * Sets the value of the mandatoAllIncasso property.
		 * 
		 */
		public void setMandatoAllIncasso(short value) {
			this.mandatoAllIncasso = value;
		}

		/**
		 * Gets the value of the brokerRefContract property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getBrokerRefContract() {
			return brokerRefContract;
		}

		/**
		 * Sets the value of the brokerRefContract property.
		 * 
		 * @param value allowed object is {@link String }
		 * 
		 */
		public void setBrokerRefContract(String value) {
			this.brokerRefContract = value;
		}

		/**
		 * Gets the value of the noCoolOff property.
		 * 
		 */
		public short getNoCoolOff() {
			return noCoolOff;
		}

		/**
		 * Sets the value of the noCoolOff property.
		 * 
		 */
		public void setNoCoolOff(short value) {
			this.noCoolOff = value;
		}

		/**
		 * Gets the value of the additionalId property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getAdditionalId() {
			return additionalId;
		}

		/**
		 * Sets the value of the additionalId property.
		 * 
		 * @param value allowed object is {@link String }
		 * 
		 */
		public void setAdditionalId(String value) {
			this.additionalId = value;
		}

		/**
		 * Gets the value of the issueCountryOfResidence property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getIssueCountryOfResidence() {
			return issueCountryOfResidence;
		}

		/**
		 * Sets the value of the issueCountryOfResidence property.
		 * 
		 * @param value allowed object is {@link String }
		 * 
		 */
		public void setIssueCountryOfResidence(String value) {
			this.issueCountryOfResidence = value;
		}

		/**
		 * Gets the value of the dateOfApplication property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getDateOfApplication() {
			return dateOfApplication;
		}

		/**
		 * Sets the value of the dateOfApplication property.
		 * 
		 * @param value allowed object is {@link String }
		 * 
		 */
		public void setDateOfApplication(String value) {
			this.dateOfApplication = value;
		}

		/**
		 * Gets the value of the dateOfCommencement property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getDateOfCommencement() {
			return dateOfCommencement;
		}

		/**
		 * Sets the value of the dateOfCommencement property.
		 * 
		 * @param value allowed object is {@link String }
		 * 
		 */
		public void setDateOfCommencement(String value) {
			this.dateOfCommencement = value;
		}

		/**
		 * Gets the value of the exPaymentTransfer property.
		 * 
		 */
		public short getExPaymentTransfer() {
			return exPaymentTransfer;
		}

		/**
		 * Sets the value of the exPaymentTransfer property.
		 * 
		 */
		public void setExPaymentTransfer(short value) {
			this.exPaymentTransfer = value;
		}

		/**
		 * Gets the value of the exMandate property.
		 * 
		 */
		public short getExMandate() {
			return exMandate;
		}

		/**
		 * Sets the value of the exMandate property.
		 * 
		 */
		public void setExMandate(short value) {
			this.exMandate = value;
		}

	}


	/**
	 * <p>
	 * Java class for anonymous complex type.
	 * 
	 * <p>
	 * The following schema fragment specifies the expected content contained within this class.
	 * 
	 * <pre>
	 * &lt;complexType&gt;
	 *   &lt;complexContent&gt;
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *       &lt;sequence&gt;
	 *         &lt;element name="Details" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *       &lt;/sequence&gt;
	 *     &lt;/restriction&gt;
	 *   &lt;/complexContent&gt;
	 * &lt;/complexType&gt;
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
			"details"
	})
	public static class ImpPonGeneralNotes {

		@XmlElement(name = "Details", required = true)
		protected String details;

		/**
		 * Gets the value of the details property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getDetails() {
			return details;
		}

		/**
		 * Sets the value of the details property.
		 * 
		 * @param value allowed object is {@link String }
		 * 
		 */
		public void setDetails(String value) {
			this.details = value;
		}

	}


	/**
	 * <p>
	 * Java class for anonymous complex type.
	 * 
	 * <p>
	 * The following schema fragment specifies the expected content contained within this class.
	 * 
	 * <pre>
	 * &lt;complexType&gt;
	 *   &lt;complexContent&gt;
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *       &lt;sequence&gt;
	 *         &lt;element name="UsrId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *       &lt;/sequence&gt;
	 *     &lt;/restriction&gt;
	 *   &lt;/complexContent&gt;
	 * &lt;/complexType&gt;
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
			"usrId"
	})
	public static class ImpUseridUsers {

		@XmlElement(name = "UsrId", required = true)
		protected String usrId;

		/**
		 * Gets the value of the usrId property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getUsrId() {
			return usrId;
		}

		/**
		 * Sets the value of the usrId property.
		 * 
		 * @param value allowed object is {@link String }
		 * 
		 */
		public void setUsrId(String value) {
			this.usrId = value;
		}

	}


	/**
	 * <p>
	 * Java class for anonymous complex type.
	 * 
	 * <p>
	 * The following schema fragment specifies the expected content contained within this class.
	 * 
	 * <pre>
	 * &lt;complexType&gt;
	 *   &lt;complexContent&gt;
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
	 *       &lt;sequence&gt;
	 *         &lt;element name="Password" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *         &lt;element name="LoginId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
	 *       &lt;/sequence&gt;
	 *     &lt;/restriction&gt;
	 *   &lt;/complexContent&gt;
	 * &lt;/complexType&gt;
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
			"password",
			"loginId"
	})
	public static class ImpValidationUsers {

		@XmlElement(name = "Password", required = true)
		protected String password;
		@XmlElement(name = "LoginId", required = true)
		protected String loginId;

		/**
		 * Gets the value of the password property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getPassword() {
			return password;
		}

		/**
		 * Sets the value of the password property.
		 * 
		 * @param value allowed object is {@link String }
		 * 
		 */
		public void setPassword(String value) {
			this.password = value;
		}

		/**
		 * Gets the value of the loginId property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getLoginId() {
			return loginId;
		}

		/**
		 * Sets the value of the loginId property.
		 * 
		 * @param value allowed object is {@link String }
		 * 
		 */
		public void setLoginId(String value) {
			this.loginId = value;
		}

	}

}
