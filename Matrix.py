from sympy import MatrixExpr

class Matrix:
    def __init__(self, m: list[list]):
        self.m = m

    def get_row(self, row: int) -> list:
        return self.m[row]

    def get_col(self, col: int) -> list:
        return [self.m[i][col] for i in range(len(self.m))]

    def get_cofactor(self, row: int, col: int) -> list:
        cofactor = []
        for i in range(len(self.m)):
            if i != row:
                cofactor.append(self.m[i][:col] + self.m[i][col + 1 :])
        return cofactor

    def constant_multiply(self, constant: int) -> list[list]:
        return [
            [constant * self.m[i][j] for j in range(len(self.m[i]))]
            for i in range(len(self.m))
        ]

    def transpose(self) -> list[list]:
        transpose = []
        for i in range(len(self.m)):
            transpose.append(self.get_col(i))
        return transpose

    def determinant(self) -> int:
        if len(self.m) == 1:
            return self.m[0][0]
        elif len(self.m) == 2:
            return self.m[0][0] * self.m[1][1] - self.m[0][1] * self.m[1][0]
        else:
            det = 0
            for i in range(len(self.m)):
                det += (
                    (-1) ** i
                    * self.m[0][i]
                    * Matrix(self.get_cofactor(0, i)).determinant()
                )
            return det

    def adjoint(self) -> list[list]:
        adjoint = []
        for i in range(len(self.m)):
            adjoint.append([])
            for j in range(len(self.m[i])):
                adjoint[i].append(
                    (-1) ** (i + j) * Matrix(self.get_cofactor(i, j)).determinant()
                )
        return Matrix(adjoint).transpose()

    def inverse(self) -> list[list]:
        det = self.determinant()
        if det == 0:
            return None
        else:
            return Matrix(self.adjoint()).constant_multiply(1 / det)

    def print_matrix(self):
        for i in range(len(self.m)):
            for j in range(len(self.m[i])):
                print(self.m[i][j], end=" ")
            print()


m = Matrix([[1, 2, 3, 4], [5, 6, 7, 3], [9, 1, 11, 12], [0, 1, 2, 3]])

m.print_matrix()

# print(m.get_cofactor(1, 2))

Matrix(m.inverse()).print_matrix()
