Feature: Hotel page process

  Background:
    Given I am on the "otel" page

  @obilet @hotel
  Scenario: Hotel filter actions
    Then The element with selector "homepage.search.button" should be clickable
    When I type "Kayseri" into the element with selector "search.departure.input" and press 'ENTER'
    And  I click on the element with selector "homepage.search.button"
    Then The page should be fully loaded

    When I click on the element with selector "hotel.filter.half.board"
    Then The element with selector "filter.selected.filter.name" should have text "Yarım Pansiyon"
    
    When I click on the element with selector "filter.sorting.dropdown"
    And  I click on the element with selector "filter.sorting.price.low.to.high"
    And  I wait for 200 milliseconds
    Then Hotel elements with selector "hotel.item.amount" should be sorted from low to high