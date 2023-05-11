# Term Project

## Project Team

Jacky Tung, jackytung20@gmail.com, github: Jacky Tung

Linxin Jiang, linxin.jiang625@gmail.com, github: LinxinJiang

Richard Park, rsjpark2@gmail.com, github: rsjpark

## App Name: PLANer

### Function 1

- A function that implements the Add Goal feature by taking in user input as the value of the Goal_object.name instance variable

### Function 2

- A function that implements the Update Goal feature by taking in user input as the value of the Goal_object.finalNumber which serves as goal progress indicators, and compares it with Goal_object.startNumber to provide a measure of the goal progression

### Function 3

- A function that allows for the user to press on and hold a goal/plan module for a given amount of time which will allow the user to move the goal/plan up or down in order

## Risks and Mitigation

- Risk 1: Lack of Android development experience

  - Desc.: May lead to trying to build functions that we're not ready for
  - Mitigation: Keeping diligent with our studies and reading up on outside resources

- Risk 2: Potential withdrawal from course from any of the team members

  - Desc.: Unforseen events causing anyone to not be able to participate in the group project
  - Mitigation: Still will have another team member remaining due to 3-person group

- Risk 3: Time management of teammates working together or individually
  - Desc.: There could be conflicts in schedules which may prevent teammates from meeting and discussing the project together
  - Mitigation: Planning ahead and keeping up communication in the discord server


## Report 1 (TDD design iteration 1)

- What we did 
  - Implemented behaviors of adding a goal and removing a goal through the realm sdk.
  - Debated on changing the UI due to different testing behaviors, such as whether or not we would like to keep the circle button or a lower profile/longer rectangle with text for the add goal button (clean icon/larger footprint vs. text for improved clarity on the functionality/smaller footprint). 
  - Return buttons navigating between activities.
  - We are looking ahead to the user stories to be created next to see if our implementation will carry on smoothly. 

- Problems we faced
  - Debugging in android studio.
  - Figuring how to implement certain things individually was fine, but integrating them with the other functionalities that we separately created was difficult
  - Final instance variable unable to be set by realm database framework, realm database framework cannot access constructor of realm object class.
  - Having trouble sending specific clicked-on goal object from myAdapter class to AssignDailyNumberActivity.  

- User Story Incomplete
  - A user story we were not able to implement this iteration is user story #4 where we had to implement a user assignment of a cumulative index to each goal. However, an activity class and inputValidator class was created.
  - The activity class is for assigning the daily number.
  - Input validator makes sure there is no empty input and non-digit input for daily number and total number.  


## Report 2 (TDD design iteration 2)

- What we did:
  - We modified some user stories and scenarios due to difficulties in implementing the original ones. 
  - We made new user stories because we found them necessary to implement
  - We implemented user story #5 where we allow users to input a deadline for the goal through a pop-up calendar and created a notice for overdue goals
  - We created espresso tests for user story #5, testing scenario 1 and 2
  - We implemented two dependent user stories #7 where we display progress bar for each goal 

- Problems we faced:
  - Large delay on modifying a goal object function, due to learning and lack of understanding of Realm.
  - Machine/Android Studio issues keeping us from working on intended project
  - Constructing tests before any implementation is impractical due to lack of familiarity with android development

- User stories incomplete: 
  - Modify Goal: unfinished, but nearly complete due to finding solution to error last minute
  - Cumulative Index: partly finished, we have the layout and instance variables but have not implemented the functionality of accumulating user goals input

- Design pattern:
  - Model-View-Controller
    - Controller: Adapter class
        - View selection: Adapter selects View view of mainActivity
        - State change: Adapter works with any changes to the goal/realm object
    - Model: Realm object (goal object)
        - Any changes to goal/realm object are reflected to the view
    - View: Android UI
        - View asks realmdb for changes in state
        - View passes on user behaviors to adapter