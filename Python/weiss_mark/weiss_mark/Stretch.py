
class Stretch:
    def __init__(self, name):
        self.currCar = None
        self.leftCar = None
        self.waitList = []
        self.leftList = []
        self.peopleOn = 0
        self.peopleOff = 0
        self.name = name

    def setCar(self, car):
        self.currCar = car
