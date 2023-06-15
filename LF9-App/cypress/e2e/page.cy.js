describe("test spec", () => {
  beforeEach(() => {
    cy.visit("http://localhost:3000/");
  });
  it("opens the home page", () => {
    cy.get("h1").should("contain.text", "Welcome to network Admin page!");
  });

  describe("city", () => {
    it("choose Hamburg and clear results", () => {
      cy.get('select[name="city"]').select("Hamburg");
      cy.get('button[name="city"]').click();
      cy.get("#result").should("contain.text", "Your results: it");
      cy.get('button[name="clear-city"]').click();
      cy.get("#result").should("not.exist");
    });
  });

  describe("department", () => {
    it("choose It and clear results", () => {
      cy.get('select[name="department"]').select("It");
      cy.get('button[name="department"]').click();
      cy.get("#result").should("contain.text", "Your results: ");
      cy.get('button[name="clear-department"]').click();
      cy.get("#result").should("not.exist");
    });
  });

  describe("newForm", () => {
    it("can create new", () => {
      cy.get("button").contains("Create new department").click();
      cy.get("#newDepartment").should("exist");
      cy.get('input[name="department"]').type("Name" + Math.random());
      cy.get('input[name="city"]').type("Kiel!!");
      cy.get("button").contains("Create").click();
      cy.get("#newDepartment").should("not.exist");
    });
  });
});
