class Sequencer:
    sequence = 0

    @classmethod
    def generate_sequence(cls):
        cls.sequence += 1
        return cls.sequence
