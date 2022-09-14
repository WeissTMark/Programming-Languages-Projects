from weiss_mark.station import Station


class Car:
    idd = 0

    def __init__(self, behavior="", location=None):
        self.id = Car.incId()
        self.people = 0
        self.location = location
        self.timeAtLoc = 0
        self.lenOfStay = 0
        self.direction = 0
        if behavior == 0:
            self.behavior = "short"
        if behavior == 1:
            self.behavior = "long"
        if behavior == 2:
            self.behavior = "adapt"

    def useBehavior(self):
        if type(self.location) == Station:
            self.timeAtLoc += 0.5
        else:
            self.timeAtLoc += 0.1
        if self.behavior == "short":
            if self.timeAtLoc >= 1.0:
                self.timeAtLoc = 0.1
                return True
        elif self.behavior == "long":
            if self.timeAtLoc >= 2.0:
                self.timeAtLoc = 0.1
                return True
        elif self.behavior == "adapt":
            if self.people < 10:
                if self.timeAtLoc >= 1.0:
                    self.timeAtLoc = 0.1
                    return True
            else:
                if self.timeAtLoc >= 2.0:
                    self.timeAtLoc = 0.1
                    return True
        if type(self.location) != Station and self.timeAtLoc >= 0.3:
            self.timeAtLoc = 0
            return True
        return False

    def getIdTypeLoad(self):
        # GRADING: SMALL
        if self.behavior == "short":
            return self.id, 1.0 - self.timeAtLoc, self.people
        # GRADING: LARGE
        if self.behavior == "long":
            return self.id, 2.0 - self.timeAtLoc, self.people
        # GRADING: ADAPTING
        if self.behavior == "adapt":
            if self.people < 10:
                return self.id, 1.0 - self.timeAtLoc, self.people
            else:
                return self.id, 2.0 - self.timeAtLoc, self.people

        return False

    def setLocation(self, location):
        self.location = location

    def loadUnload(self, peopleOn, peopleOff):
        self.people -= peopleOff
        if self.people < 0:
            self.people = 0
        if self.people + peopleOn <= 20:
            self.people += peopleOn
        elif self.people + peopleOn > 20:
            left = (self.people + peopleOn) - 20
            self.people = 20
            return left

    @staticmethod
    def incId():
        Car.idd += 1
        return Car.idd

    def __str__(self):
        carInfo = self.getIdTypeLoad()
        if carInfo:
            if type(self.location) == Station:
                return "[ ID:" + str(carInfo[0]).ljust(4) + str(carInfo[1]) + "min " + str(
                    carInfo[2]) + "/20 ]"
            else:
                return "[ ID:" + str(carInfo[0]).ljust(4) + "{:1.1f}".format(self.timeAtLoc) + " ]"
        else:
            return ""
