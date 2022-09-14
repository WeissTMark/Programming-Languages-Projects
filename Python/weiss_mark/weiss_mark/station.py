
class Station:
    def __init__(self, name):
        self.currCar = None
        self.leftCar = None
        self.waitList = []
        self.leftList = []
        self.name = name
        self.peopleOn = 0
        self.peopleOff = 0

    def setPeopleMove(self, on, off):
        self.peopleOn = on
        self.peopleOff = off

    def setCar(self, car):
        self.currCar = car
