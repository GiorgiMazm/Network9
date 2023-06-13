describe("test spec", () => {
  beforeEach(() => {
    cy.visit("http://localhost:3000/");
  });
  it("opens the home page", () => {
    cy.get("h1").should("contain.text", "Welcome to network Admin page!");
  });

  describe("city", () => {
    it("choose Lubeck and clear results", () => {
      cy.get('select[name="city"]').select("Lubeck");
      cy.get('button[name="city"]').click();
      cy.get("#result").should("contain.text", "Your results: Lubeck");
      cy.get('button[name="clear-city"]').click();
      cy.get("#result").should("not.exist");
    });
  });

  describe("department", () => {
    it("choose Verkauf and clear results", () => {
      cy.get('select[name="department"]').select("Verkauf");
      cy.get('button[name="department"]').click();
      cy.get("#result").should("contain.text", "Your results: Verkauf");
      cy.get('button[name="clear-department"]').click();
      cy.get("#result").should("not.exist");
    });
  });

  describe("newForm", () => {
    it("can create new", () => {
      cy.get("button").contains("Create new department").click();
      cy.get("#newDepartment").should("exist");
      cy.get('input[name="department"]').type(
        "New department " + Math.random()
      );
      cy.get('input[name="city"]').type("Kiel!!");
      cy.get("button").contains("Create").click();
      cy.get("#newDepartment").should("not.exist");
    });
  });
});
