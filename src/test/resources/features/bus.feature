Feature: Bus page process

  Background:
    Given I am on the homepage

  @obilet @bus
  Scenario: Buying a bus ticket
    Then The element with selector "homepage.search.button" should be clickable
    When I type "Pınarbaşı" into the element with selector "search.departure.input" and press 'ENTER'
    And  I click on the element with selector "homepage.search.button"
    Then The page should be fully loaded

    When I click on the element with selector "popup.close.button"

    And  I click on the element with selector "bus.select.seat.button"
    And  I store text from element with selector "bus.open.departure.hour" as "departureHour"
    And  I store text from element with selector "bus.open.departure.location" as "departureLocation"
    And  I store text from element with selector "bus.open.arrival.location" as "arrivalLocation"
    And  I store attribute "alt" from element with selector "bus.open.partner.name" as "selectedPartnerName"
    And  I click on the element with selector "bus.available.seats"
    And  I click on the element with selector "bus.select.male"
    And  I store text from element with selector "bus.selected.seat" as "selectedSeat"
    And  I store text from element with selector "bus.open.amount" as "amount"
    And  I click on the element with selector "bus.approved.and.continue.button"
    And  I click on the element with selector "bus.modal.standart.button"

    Then The page should be fully loaded

    And  I store text from element with selector "bus.checkout.partner.name" as "checkoutPartnerName"
    And  I store text from element with selector "bus.checkout.seat.number" as "checkoutSeatNumber"
    And  I store text from element with selector "bus.checkout.amount" as "checkoutAmount"
    And  I store text from element at index 0 with selector "bus.checkout.intro.table.td" as "checkoutDepartureLocation"
    And  I store text from element at index 1 with selector "bus.checkout.intro.table.td" as "checkoutArrivalLocation"
    And  I store text from element at index 2 with selector "bus.checkout.intro.table.td" as "checkoutDate"

    Then The stored variable "departureLocation" should equal stored variable "checkoutDepartureLocation"
    And  The stored variable "arrivalLocation" should equal stored variable "checkoutArrivalLocation"
    And  The stored variable "amount" should equal stored variable "checkoutAmount"
    And  The stored variable "selectedPartnerName" should equal stored variable "checkoutPartnerName"
    And  The stored variable "selectedSeat" should equal stored variable "checkoutSeatNumber"
    And  The stored variable "checkoutDate" should contain stored variable "departureHour"